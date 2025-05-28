package jky.aluguelfacilcarros;

import jky.aluguelfacilcarros.Tabela;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class PanelAlugar extends JFrame {
    private JPanel AlugarForm;
    // Header
    private JPanel rotasGetVeiculos;
    private JPanel rotasAddVeiculos;
    private JPanel rotasAddUser;
    private JPanel Header;
    private JButton LinkUserAdd;
    private JButton LinkAddVeiculo;
    private JButton LinkGetVeiculo;
    // Adicionar User
    private JTextField CPFTextField;
    private JTextField nomeTextField;
    private JTextField dataDeNascimentoTextField;
    private JButton adicionarUsuárioButton;
    private JLabel UserIcon;
    // Adicionar Veiculo
    private JTextField placaTextField;
    private JTextField modeloTextField;
    private JTextField corDeCarroTextField;
    private JTextField dataDaCriaçãoTextField;
    private JTextField preçoDaDiáriaTextField;
    private JButton adicionarCarroButton;
    // Listar Veiculo
    private JButton removerButton;
    private JButton ALUGARButton;
    private JButton devolverButton;
    private JComboBox comboBox1;
    private JButton FILTRARButton;
    private JLabel listVeiculo;
    private JTable table1;


    // Função dos Botões do Header
    public void Header(JButton LinkHeader){
        rotasGetVeiculos.setVisible(false);
        rotasAddVeiculos.setVisible(true);
        rotasAddUser.setVisible(false);

        LinkHeader.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotasGetVeiculos.setVisible(false);
                rotasAddVeiculos.setVisible(false);
                rotasAddUser.setVisible(false);

                if(LinkHeader.getText().equals("Adicionar Veiculos")){
                    rotasAddVeiculos.setVisible(true);
                } else if (LinkHeader.getText().equals("Adicionar User")){
                    rotasAddUser.setVisible(true);
                }else if(LinkHeader.getText().equals("Lista de Veiculos")){
                    rotasGetVeiculos.setVisible(true);
                }

            }
        });
    }

    // Função para Customizar o Input e Gerar o Placeholder
    public void Input_Padronizado(JTextField input,String placeholder){
        Border border = new LineBorder(Color.white,2,true);
        input.setBorder(border);
        input.setText(placeholder);

        input.addFocusListener(new FocusAdapter() {
            // Se O input estiver sendo Usado o Placeholder Desaparece
            @Override
            public void focusGained(FocusEvent e) {
                if(input.getText().equals(placeholder)){
                    input.setText("");
                }
            }

            // Se O input não estiver sendo Usado e Estiver Vazio o Placeholder Aparece
            @Override
            public void focusLost(FocusEvent e) {
                if(input.getText().isEmpty()){
                    input.setText(placeholder);
                }
            }
        });
    }

    // Alerta Função
    public static void messageAlert(String text,String title){
        JOptionPane optionPane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(title);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public PanelAlugar(String titulo,int width,int height){
        Tabela tabela = new Tabela();
        setContentPane(AlugarForm);
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        // A Janela Vai ficar em cima de todas as outras
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // Headers
        Header(LinkGetVeiculo);
        Header(LinkAddVeiculo);
        Header(LinkUserAdd);

        // Input Modificar
        Input_Padronizado(placaTextField,"Placa:");
        Input_Padronizado(modeloTextField,"Modelo:");
        Input_Padronizado(corDeCarroTextField,"Cor:");
        Input_Padronizado(dataDaCriaçãoTextField,"Ano do carro:");
        Input_Padronizado(preçoDaDiáriaTextField,"Preço da Diária do Carro:");

        // Image do AddUser
        ImageIcon image = new ImageIcon(new ImageIcon("C:/Users/ronal/OneDrive/Imagens/Programação/Java/AluguelFacilCarros/assets/desconhecido.jpg")
                .getImage().getScaledInstance(250, 250, Image.SCALE_FAST));
        UserIcon.setIcon(image);

        comboBox1.addItem("Modelo");
        comboBox1.addItem("Fabricante");
        comboBox1.addItem("Preço do Carro");

//        adicionarCarroButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                ConexaoDB con = new ConexaoDB();
//                addCarro(placaTextField.getText(),modeloTextField.getText(),dataDaCriaçãoTextField.getText(),corDeCarroTextField.getText(),preçoDaDiáriaTextField.getText())
//
//                messageAlert("Carro adicionado com sucesso!","Carro adicionado");
//            }
//        });

        // Table


        tabela.Tabela(table1);

        setVisible(true);
    }

    public static void main(String[] args) {

        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB

        con.resetarDB();
        con.closeConnection();

        new PanelAlugar("Aluguel Facil Carros",1920,1080);


    }
}
