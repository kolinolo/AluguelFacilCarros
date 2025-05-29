package jky.aluguelfacilcarros;


import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {


            ConexaoDB con = new ConexaoDB();
            con.alugaCarro(1,"kolom",4);
            con.resetarDB();
    }

}
