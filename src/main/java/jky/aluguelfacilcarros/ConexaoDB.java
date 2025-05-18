package jky.aluguelfacilcarros;

// Operações do SQLite
import java.sql.Connection; // OBJ da conexão
import java.sql.DriverManager; // operações SQL
import java.sql.SQLException; // exceptions do SQLite
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    public void add(){
        // Versão Teste da Função Adicionar

        try(Statement stmt = this.connection.createStatement()){

            stmt.executeUpdate("INSERT INTO CARROS values ('6035','Navio do Roberto Carlos','13-01-2023',25256,15)");

        }catch (SQLException e){
            System.out.println(e.getMessage());;
        }

    }

    public List<String> Select(){
        List<String> carModels = new ArrayList<>();
        try(Statement stmt = this.connection.createStatement()){
            var res = stmt.executeQuery("SELECT * FROM CARROS");
            while (res.next()){
                carModels.add(res.getString("modelo"));
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


            stmt.executeUpdate("DROP TABLE IF EXISTS Modelos;");
            stmt.executeUpdate("DROP TABLE IF EXISTS CoresCarros;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Aluguel;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Carros;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Cliente;");

            stmt.executeUpdate(// Modelos
                    "CREATE TABLE Modelos (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL," +
                    "autonomia REAL NOT NULL)"
            );

            stmt.executeUpdate( "INSERT INTO Modelos (nome, autonomia) VALUES" +
                                    " ('celta', 10.00)," +
                                    " ('Palio', 11.50)," +
                                    " ('HB20', 15.35)");



            stmt.executeUpdate(// Cores dos carros
                    "CREATE TABLE CoresCarros (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL)"
            );

            stmt.executeUpdate( "INSERT INTO CoresCarros (nome) VALUES" +
                    " ('cinza')," +
                    " ('vermelho')," +
                    " ('preto'),"+
                    " ('branco')");




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
                        "modelo INTEGER, " +
                        "fabricacao DATE, " +
                        "cor_carro INTEGER," +
                        "precoDIaria REAL," +
                        "FOREIGN KEY (cor_carro) REFERENCES CoresCarros(nome))"
            );

            // Tabela Aluguel
            stmt.executeUpdate(
                    "CREATE TABLE Aluguel (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "cliente_id INTEGER NOT NULL, " +
                        "carro_placa TEXT NOT NULL, " +
                        "data_aluguel DATE, " +
                        "data_prazo DATE, " +
                        "data_devolucao DATE, " +
                        "FOREIGN KEY (cliente_id) REFERENCES Cliente(ID), " +
                        "FOREIGN KEY (carro_placa) REFERENCES Carros(placa))");









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