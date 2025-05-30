package jky.aluguelfacilcarros;

// Operações do SQLite
import java.sql.Connection; // OBJ da conexão
import java.sql.DriverManager; // operações SQL
import java.sql.SQLException; // exceptions do SQLite
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static jky.aluguelfacilcarros.PanelAlugar.messageAlert;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class ConexaoDB {

    private Connection connection;


    public ConexaoDB() {

        String url = "jdbc:sqlite:AluguelFacilDB.db"; // URL do DB

        try {

            connection = DriverManager.getConnection(url);

            System.out.println("Conectado ao DB com sucesso!");

        } catch (SQLException e) {

            System.out.println("Algo de errado ocorreu ao se conectar com o DB:\n" + e.getMessage());


        }

    }


    public Connection getConnection() {

        return connection; // retorna o OBJ de conexão do DB

    }


    public void closeConnection() { //fecha a conexão do OBJ do DB

        try {

            if (connection != null) {

                System.out.println("Connection Closed");

                connection.close();

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());;

        }

    }

     public void addPessoa(String nome, String cpf, String dataNascimentoSTR) throws SQLException {

         Statement stmt = this.connection.createStatement();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         LocalDate dataNascimento = LocalDate.parse(dataNascimentoSTR, formatter);


         String sql = "INSERT INTO  CLIENTE (nome, CPF, data_nascimento) values ('" + nome + "', '" + cpf + "', '" + dataNascimento + "')";
         System.out.println(sql);
         stmt.executeUpdate(sql);



     };

    public void addCarro(String placa,
                         String modelo,
                         int anoFabricacao,
                         String cor,
                         Float preco){

        // Versão Teste da Função Adicionar

        try(Statement stmt = this.connection.createStatement()){


            String sql = "INSERT INTO CARROS VALUES ('" + placa + "', '" + modelo + "', " + anoFabricacao + ", '" + cor + "', " + preco + ",0)";
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            System.out.println(e.getMessage());;
        }






    }

    public void alugaCarro(int ID, String placa, int dias) throws SQLException {

        Statement stmt = this.connection.createStatement();

        String sql = "SELECT 1 FROM carros WHERE placa like '%" + placa + "%' and alugado = 1 LIMIT 1";
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            messageAlert("Carro indisponível!","Carro adicionado");



        } else {


            System.out.println(sql);
            sql = "INSERT INTO Aluguel (cliente_id, carro_placa, data_prazo, data_aluguel, data_devolucao) VALUES (" + ID + ",' " + placa + "',date('now', '+" + dias + " days')," + "date('now'), NULL)";
            stmt.executeUpdate(sql);
            System.out.println(sql);


            sql = "update Carros set alugado = 1 where placa = '" + placa + "'";

            System.out.println(sql);
            stmt.executeUpdate(sql);
            messageAlert("Carro alugado com sucesso!","Carro adicionado");

        }






    }

    public void devolverCarro(String placa) throws SQLException {

        Statement stmt = this.connection.createStatement();

        //ChronoUnit.DAYS.between(data1, data2)
        int dias = 4;
        float preco = 2.1F;
        String sql = "update Aluguel set data_devolucao = date('now') where carro_placa like '%" + placa + "%'";
        System.out.println(sql);
        stmt.executeUpdate(sql);
        sql= "select Carros.precoDIaria from Carros where placa like '%" + placa+ "%';";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {

            preco =rs.getFloat("precoDIaria");
        }

        messageAlert("Carro devolvido pelo preço de R$" + (dias * preco),"Carro devolvido");

    };


    public List<String> Select(String modelo,String tabela){
        List<String> tabelaModels = new ArrayList<>();
        try(Statement stmt = this.connection.createStatement()){
            var res = stmt.executeQuery(String.format("SELECT %s FROM %s",modelo,tabela));
            while (res.next()){
                tabelaModels.add(res.getString(modelo));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());;
        }
        return tabelaModels;
    }

    public List<String> ColumnSelect(String tabela){
        List<String> tabelas = new ArrayList<>();
        try(Statement stmt = this.connection.createStatement()){
            ResultSet Column = stmt.executeQuery(String.format("SELECT * FROM %s LIMIT 1",tabela));

            ResultSetMetaData metaData = Column.getMetaData();
            int Tamanho = metaData.getColumnCount();

            for (int i = 1; i <= Tamanho; i++) {
                tabelas.add(metaData.getColumnName(i));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());;
        }

        return tabelas;
    }


    public void resetarDB(){

        try (Statement stmt = this.connection.createStatement()) {

            // Desativa verificação de chaves estrangeiras
            stmt.execute("PRAGMA foreign_keys = OFF;");
            connection.setAutoCommit(false);

            // Busca todas as tabelas que não são internas do SQLite
            ResultSet rs = stmt.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';"
            );

            while (rs.next()) {
                String tableName = rs.getString("name");
                String dropSQL = "DROP TABLE IF EXISTS \"" + tableName + "\";";
                stmt.executeUpdate(dropSQL);
                System.out.println("Tabela \"" + tableName + "\" apagada.");
            }



            stmt.executeUpdate("DROP TABLE IF EXISTS CoresCarros;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Aluguel;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Carros;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Cliente;");



            // Tabela Cliente
            stmt.executeUpdate(
                    "CREATE TABLE Cliente (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL, " +
                        "CPF TEXT NOT NULL, " +
                        "data_nascimento DATE)"
        );

            // Tabela Carros
            stmt.executeUpdate(
                    "CREATE TABLE Carros (" +
                        "placa TEXT PRIMARY KEY, " +
                        "modelo TEXT, " +
                        "fabricacao integer, " +
                        "cor_carro TEXT," +
                        "precoDIaria REAL," +
                        "alugado integer)"

            );

            // Tabela Aluguel
            stmt.executeUpdate(
                    "CREATE TABLE Aluguel (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "cliente_id INTEGER NOT NULL, " +
                        "carro_placa TEXT NOT NULL, " +
                        "data_aluguel DATE, " +
                        "data_prazo DATE, " +
                        "data_devolucao DATE)");









            connection.commit();
            System.out.println("Banco de dados resetado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao resetar o banco de dados: " + e.getMessage());

            try {
                connection.rollback(); //TENTA reverte a conexão em caso de erro
            } catch (SQLException ex) {
                System.out.println("Erro ao desfazer mudanças: " + ex.getMessage()); // deu ruim do Ruim
            }
        }



    }
}