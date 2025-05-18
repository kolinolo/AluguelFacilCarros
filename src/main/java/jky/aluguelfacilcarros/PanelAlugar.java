package jky.aluguelfacilcarros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PanelAlugar extends JFrame {
    private JPanel AlugarForm;
    private JList list1;
    private JPanel rotasGetVeiculos;
    private JPanel rotasAddVeiculos;
    private JButton button1;
    private JPanel rotasAddUser;
    private JTextField textField1;
    private JButton button2;
    private JPanel Header;
    private JButton AddVeiculosBtn;
    private JButton GetVeiculosBtn;
    private JButton AddUSerBtn;

    public void Header(){
        rotasGetVeiculos.setVisible(false);
        rotasAddVeiculos.setVisible(false);
        rotasAddUser.setVisible(false);

        AddVeiculosBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotasAddVeiculos.setVisible(true);
                rotasGetVeiculos.setVisible(false);
                rotasAddUser.setVisible(false);
            }
        });

        GetVeiculosBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotasAddVeiculos.setVisible(false);
                rotasGetVeiculos.setVisible(true);
                rotasAddUser.setVisible(false);
            }
        });

        AddUSerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotasAddVeiculos.setVisible(false);
                rotasGetVeiculos.setVisible(false);
                rotasAddUser.setVisible(true);
            }
        });
    }

    public PanelAlugar(){
        setContentPane(AlugarForm);
        setTitle("Aluguel Facil Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        // Criação de uma lista dinâmica
        ArrayList<String> list = new ArrayList<>();

        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB

        // Adicionando itens à lista
        for (int i = 0; i < con.Select().size(); i++){
            list.add(con.Select().get(i));
        }

        // Convertendo a lista para um array
        String[] lista = new String[list.size()];
        lista = list.toArray(lista);

        list1.setListData(lista);

        Header();
        setVisible(true);
    }

    public static void main(String[] args) {
        new PanelAlugar();
    }
}
