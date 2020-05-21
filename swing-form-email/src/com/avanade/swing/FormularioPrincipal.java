package com.avanade.swing;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FormularioPrincipal extends JFrame {
	
	private static final int FORM_LARGURA = 600;
	private static final int FORM_ALTURA = 300;
	
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		FormularioPrincipal principal = new FormularioPrincipal();
		principal.setVisible(true);
	}
	
	public FormularioPrincipal(){
		this.iniciarForm();
		
	}
	private void iniciarForm() {
		setTitle("Meu email");
		setSize(FORM_LARGURA, FORM_ALTURA);
		
		JLabel enviarPara = new JLabel("Enviar para");
		JLabel copiaPara = new JLabel("Copia para");
		JLabel assunto = new JLabel("Assunto");
		JTextField textEnviar = new JTextField(45);
		JTextField textCopia = new JTextField(45);
		JTextField textAssunto = new JTextField(35);
		JLabel mensagem = new JLabel("Mensagem");
		JTextArea textEmail = new JTextArea (10, 40);
		
		JButton btnEnviar = new JButton("Enviar");
		
		setLayout(new FlowLayout());
		getContentPane().add(enviarPara);
		getContentPane().add(textEnviar);
		getContentPane().add(copiaPara);
		getContentPane().add(textCopia);
		getContentPane().add(assunto);
		getContentPane().add(textAssunto);
		getContentPane().add(btnEnviar);
		getContentPane().add(mensagem);
		getContentPane().add(textEmail);
		
	}
	
}
