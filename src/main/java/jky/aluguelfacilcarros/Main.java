package jky.aluguelfacilcarros;


public class Main {

    public static void main(String[] args) {

        boolean resetarDB = false;
        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB

        if (resetarDB){

            con.resetarDB();

        }

//        con.add();
        System.out.println(con.listar());


    }

}
