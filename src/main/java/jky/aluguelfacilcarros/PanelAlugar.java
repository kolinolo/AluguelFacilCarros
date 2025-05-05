package jky.aluguelfacilcarros;

import javax.swing.*;

public class PanelAlugar extends JFrame {
    private JPanel AlugarForm;
    private JList list1;

    public PanelAlugar(){
        setContentPane(AlugarForm);
        setTitle("Aluguel Facil Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        String listas[] = {"Subaru muito pica","C3","Robt CL"};

        list1.setListData(listas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new PanelAlugar();
    }
}
