package jky.aluguelfacilcarros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Tabela {

    public void Tabela(JTable tabela){
        tabela.setModel(new DefaultTableModel(20,5));
        // Criar a Column
        String[] Header = {"Placa","Modelo","Fabricação","Cor do Carro","Preço do Carro"};
        String[] data = {"placa","modelo","fabricacao","cor_carro","precoDIaria"};
        CriarHeader(Header,tabela);
        InserirItem(tabela,data);
    }

    public void CriarHeader(String [] Headers,JTable tabela){
        for (int i = 0; i < Headers.length; i++){
            tabela.setValueAt(Headers[i],0,i);
        }
    }

    public void InserirItem(JTable tabela,String[] data){
        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB

        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < con.Select("modelo").size(); j++){
                System.out.println(con.Select(data[i]).get(j));
                tabela.setValueAt(con.Select(data[i]).get(j),j + 1,i);
            }
            }
    }
}
