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


            String sql = "INSERT INTO CARROS VALUES ('" + placa + "', '" + modelo + "', " + anoFabricacao + ", '" + cor + "', " + preco + ")";
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            System.out.println(e.getMessage());;
        }

    }

    public List<String> Select(String modelo){
        List<String> carModels = new ArrayList<>();
        try(Statement stmt = this.connection.createStatement()){
            var res = stmt.executeQuery(String.format("SELECT %s FROM CARROS",modelo));
            while (res.next()){
                carModels.add(res.getString(modelo));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());;
        }
        return carModels;
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
                        "precoDIaria REAL)"

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