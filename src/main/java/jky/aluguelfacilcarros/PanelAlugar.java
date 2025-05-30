package jky.aluguelfacilcarros;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;


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
    private JButton alugarButton;
    private JButton devolverButton;
    private JComboBox ComboTabela;
    private JLabel listVeiculo;
    private JTable Tabela;
    // Alugar Tela
    private JPanel AlugarTela;
    private JComboBox ID;
    private JComboBox Placa;
    private JTextField DiasInput;
    private JButton VOLTAR;
    private JButton btnAlugacarro;

    public void AlugarClick(){
        AlugarTela.setVisible(false);
        alugarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AlugarTela.setVisible(true);
                rotasGetVeiculos.setVisible(false);
                rotasAddVeiculos.setVisible(false);
                rotasAddUser.setVisible(false);
                Header.setVisible(false);
            }
        });
    }

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


        AlugarClick();
        // Headers
        Header(LinkGetVeiculo);
        Header(LinkAddVeiculo);
        Header(LinkUserAdd);

        // Input Modificar
        Input_Padronizado(placaTextField," Placa:");
        Input_Padronizado(modeloTextField," Modelo:");
        Input_Padronizado(corDeCarroTextField," Cor:");
        Input_Padronizado(dataDaCriaçãoTextField," Ano do carro:");
        Input_Padronizado(preçoDaDiáriaTextField," Preço da Diária do Carro:");
        Input_Padronizado(CPFTextField," CPF:");
        Input_Padronizado(nomeTextField," Nome do Usuário: ");
        Input_Padronizado(dataDeNascimentoTextField," Data de Nascimento: ");
        Input_Padronizado(DiasInput,"Dias a Ser Alugado: ");

        // Image do AddUser
        ImageIcon image = new ImageIcon(new ImageIcon("assets/desconhecido.jpg")
                .getImage().getScaledInstance(250, 250, Image.SCALE_FAST));
        UserIcon.setIcon(image);

        tabela.Combos(ComboTabela,Tabela);

        tabela.CreateCombo(ID,"ID","Cliente");
        tabela.CreateCombo(Placa,"placa","Carros");

        adicionarCarroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConexaoDB con = new ConexaoDB();
                con.addCarro(placaTextField.getText(),
                        modeloTextField.getText(),
                        Integer.parseInt(dataDaCriaçãoTextField.getText()),
                        corDeCarroTextField.getText(),
                        Float.valueOf(preçoDaDiáriaTextField.getText()));

                placaTextField.setText(" Placa:");
                modeloTextField.setText(" Modelo:");
                corDeCarroTextField.setText(" Cor:");
                dataDaCriaçãoTextField.setText(" Ano do carro:");
                preçoDaDiáriaTextField.setText(" Preço da Diária do Carro:");

                messageAlert("Carro adicionado com sucesso!","Carro adicionado");
            }
        });


        btnAlugacarro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConexaoDB con = new ConexaoDB();


                int idPessoa = Integer.parseInt((String) Objects.requireNonNull(ID.getSelectedItem()));

                System.out.println(ID.getSelectedItem());
                System.out.println((String) Placa.getSelectedItem());
                System.out.println(Integer.parseInt(DiasInput.getText()));

                try {
                    con.alugaCarro(idPessoa,
                            (String) Placa.getSelectedItem(),
                            Integer.parseInt(DiasInput.getText()));

                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }

                messageAlert("Carro adicionado com sucesso!","Carro adicionado");
            }
        });





        VOLTAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotasGetVeiculos.setVisible(false);
                rotasAddVeiculos.setVisible(true);
                rotasAddUser.setVisible(false);
                Header.setVisible(true);
                AlugarTela.setVisible(false);
            }
        });

        adicionarUsuárioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConexaoDB con = new ConexaoDB();
                try {
                    con.addPessoa(nomeTextField.getText(),CPFTextField.getText(),dataDeNascimentoTextField.getText());
                    CPFTextField.setText(" CPF:");
                    nomeTextField.setText(" Nome do Usuário:");
                    dataDeNascimentoTextField.setText(" Data de Nascimento:");
                    DiasInput.setText("Dias a Ser Alugado:");
                } catch (SQLException ex) {
                    messageAlert(String.valueOf(ex),"ERRO");
                    throw new RuntimeException(ex);
                }



                messageAlert("Usuário adicionado com sucesso!","Usuário adicionado");
            }
        });

        // Table


        tabela.Tabela(Tabela);



        setVisible(true);
    }

    public static void main(String[] args) {

        ConexaoDB con = new ConexaoDB(); // inicia o OBJ de conexão com o DB

//        con.resetarDB();
        con.closeConnection();

        new PanelAlugar("Aluguel Facil Carros",900,900);


    }

}
