package jky.aluguelfacilcarros;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Tabela {

    public void Tabela(JTable tabela){
        // Criar a Column
        CriarHeader(tabela,"Carros");
        InserirItem(tabela,"Carros");
    }

    public void CriarHeader(JTable tabela,String ColumnName){
        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB
        String[] Headers = con.ColumnSelect(ColumnName).toArray(new String[0]);

        tabela.setModel(new DefaultTableModel(25,Headers.length));

        for (int i = 0; i < Headers.length; i++){
            tabela.setValueAt(Headers[i].toUpperCase(Locale.ROOT),0,i);
        }

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (row == 0) {
                    setBackground(Color.RED);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(null);
                }
                return this;
            }
        });
    }

    public void InserirItem(JTable tabela,String ColumnName){
        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB
        String[] data = con.ColumnSelect(ColumnName).toArray(new String[0]);

        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < con.Select(data[0],ColumnName).size(); j++) {
                tabela.setValueAt(con.Select(data[i], ColumnName).get(j), j + 1, i);
            }
        }
    }

    public void Combos(JComboBox combo,JTable tabela){
        combo.addItem("Carros");
        combo.addItem("Aluguel");
        combo.addItem("Cliente");

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriarHeader(tabela,combo.getSelectedItem().toString());
                InserirItem(tabela,combo.getSelectedItem().toString());
            }
        });

    }
}
