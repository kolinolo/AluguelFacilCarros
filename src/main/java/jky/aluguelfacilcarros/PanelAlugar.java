package jky.aluguelfacilcarros;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelAlugar extends JFrame {
    private JPanel AlugarForm;
    private JList list1;
    private JPanel rotasGetVeiculos;
    private JPanel rotasAddVeiculos;
    private JPanel rotasAddUser;
    private JPanel Header;
    private JButton AddVeiculosBtn;
    private JButton GetVeiculosBtn;
    private JButton AddUSerBtn;
    private JTextField CPFTextField;
    private JTextField nomeTextField;
    private JTextField dataDeNascimentoTextField;
    private JButton adicionarUsuárioButton;
    private JLabel UserIco;
    private JTextField placaTextField;
    private JTextField modeloTextField;
    private JTextField corDeCarroTextField;
    private JTextField dataDaCriaçãoTextField;
    private JTextField preçoDaDiáriaTextField;
    private JButton adicionarCarroButton;
    private JButton removerButton;
    private JButton ALUGARButton;
    private JButton devolverButton;
    private JComboBox comboBox1;
    private JButton FILTRARButton;

    public void Header(){
        rotasGetVeiculos.setVisible(false);
        rotasAddVeiculos.setVisible(true);
        rotasAddUser.setVisible(false);

        AddVeiculosBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotasAddVeiculos.setVisible(true);
                rotasGetVeiculos.setVisible(false);
                rotasAddUser.setVisible(false);
            }
        });

        ImageIcon image = new ImageIcon("C:/Users/ronal/OneDrive/Imagens/Programação/Java/AluguelFacilCarros/assets/Desconhecido.jpg");

        UserIco.setIcon(image);
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

    public static void PadronInput(JTextField input) {
        Border border = new LineBorder(Color.white, 1, true);
        input.setBorder(border);

        if(input.getText().isEmpty()){
            System.out.println("Vazio");
        }
    }

    public void ModificarInputs(){
        PadronInput(placaTextField);
        PadronInput(modeloTextField);
        PadronInput(corDeCarroTextField);
        PadronInput(dataDaCriaçãoTextField);
        PadronInput(preçoDaDiáriaTextField);
    }

    public PanelAlugar(){
        setContentPane(AlugarForm);
        setTitle("Aluguel Facil Carros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        ModificarInputs();
        // Criação de uma lista dinâmica
        ArrayList<String> list = new ArrayList<>();

        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB

        // Adicionando itens à lista
        for (int i = 0; i < con.Select("modelo").size(); i++){
            list.add(con.Select("modelo").get(i) + " - " + con.Select("placa").get(i));
        }

        comboBox1.addItem("Carro");
        comboBox1.addItem("Moto");
        comboBox1.addItem("Barco");
        comboBox1.addItem("Avião");
        comboBox1.addItem("Trem");
        comboBox1.addItem("Bicicleta");
        comboBox1.addItem("Camelo");
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
