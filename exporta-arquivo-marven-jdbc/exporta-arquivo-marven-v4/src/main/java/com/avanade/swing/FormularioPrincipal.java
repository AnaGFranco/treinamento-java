package com.avanade.swing;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FormularioPrincipal implements ActionListener {
	
	private static final int FORM_LARGURA = 530;
	private static final int FORM_ALTURA = 200;

    private JPanel mainPanel;
    private JLabel mainLabel;
    
    JLabel lbLogin = new JLabel("Login");
	JLabel lbSenha = new JLabel("Senha");
    JTextField campoLogin = new JTextField(40);
    JTextField campoSenha = new JTextField(40);
    JButton acessar = new JButton("Acessar");


    public FormularioPrincipal(){
      
       mainLabel = new JLabel();

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.ORANGE);

        mainPanel.add(mainLabel);
        adicionaCamposLogin();

    }

    private void adicionaCamposLogin(){
    	

        acessar.addActionListener(this);
        
        mainPanel.add(lbLogin);
        mainPanel.add(campoLogin);
        mainPanel.add(lbSenha);
        mainPanel.add(campoSenha);
        mainPanel.add(acessar);

    }

    private static void criaExibeJanelaLogin() {
    	
    	   	
        //Seta decoração DEFAULT
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Cria uma nova instancia da janela
        FormularioPrincipal carregarJanela = new FormularioPrincipal();

        //Cria e organiza a janela.
        JFrame carregarMainFrame = new JFrame("Efetuar login");
        carregarMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        carregarMainFrame.setContentPane(carregarJanela.mainPanel); // carrega conteudo da janela

        //Exibe janela.
        carregarMainFrame.pack();
        carregarMainFrame.setSize(FORM_LARGURA, FORM_ALTURA);
        carregarMainFrame.setVisible(true);

    }


	public static void main(String[] args) {
       
            criaExibeJanelaLogin();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		        
	//TODO
	if(acessar.getText().equals("Acessar"))
		
		if(campoLogin.getText().trim().isEmpty()) {
			JLabel lbPreencha = new JLabel("Preencha o Login!!");
			mainPanel.add(lbPreencha);
		}
	else
		acessar.setText("Acessado");		
	}

   

}