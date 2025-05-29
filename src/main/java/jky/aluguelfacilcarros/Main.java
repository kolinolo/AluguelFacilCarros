package jky.aluguelfacilcarros;


import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {


            ConexaoDB con = new ConexaoDB();

        try {
            con.alugaCarro(1,"koli", 5);

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

}
