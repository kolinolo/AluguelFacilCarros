package jky.aluguelfacilcarros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        String listas[] = {"Subaru muito pica","C3","Robt CL"};

        list1.setListData(listas);

        Header();
        setVisible(true);
    }

    public static void main(String[] args) {
        new PanelAlugar();
    }
}
