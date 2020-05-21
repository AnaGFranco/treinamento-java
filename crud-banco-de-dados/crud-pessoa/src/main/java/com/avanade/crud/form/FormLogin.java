package com.avanade.crud.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.UsuarioDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.UsuarioModel;

public class FormLogin extends JDialog{

	private static final long serialVersionUID = 6425497622881335328L;
	
	private static final Logger LOG = LoggerFactory.getLogger(FormLogin.class);

	private JPanel pnlTitulo;
	
	private JPanel pnlForm;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	
	private JPanel pnlBotoes;
	private JButton btnEntrar;
	private JButton btnCancelar;
	
	public FormLogin() {
		super();
		this.iniciar();
	}

	private void iniciar() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //necessario para quando a applicação fechar
		this.setLayout(new BorderLayout(10,10));
		this.getContentPane().add(getPnlTitulo(), BorderLayout.NORTH);
		this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
		this.getContentPane().add(getPnlBotoes(), BorderLayout.SOUTH);
		this.setResizable(false);
	
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.incluirEventos();
	}
	
	
	private void incluirEventos() {
		
		btnCancelar.addActionListener(l ->{
			this.setVisible(false);
			this.dispose();
		});
		btnEntrar.addActionListener(l -> this.entrar());
	}
	
	private JPanel getPnlTitulo() {
		if (pnlTitulo == null) {
			
			pnlTitulo = new JPanel();
			pnlTitulo.setLayout(new FlowLayout());		
			
			JLabel lblTitulo = new JLabel("Identifique-se");
			
			pnlTitulo.add(lblTitulo);
		}
		return pnlTitulo;
	}
	
	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel();
			pnlForm.setLayout(new GridLayout(2,2,10,10)); //Cria colunas e linhas
			
			
			JLabel lblUsuario = new JLabel("  Usuário:"); 
			txtUsuario = new JTextField();
			
			JLabel lblSenha = new JLabel("  Senha:"); 
			txtSenha = new JPasswordField(); // mostrar senha mascarada
			
			pnlForm.add(lblUsuario);
			pnlForm.add(txtUsuario);
			
			pnlForm.add(lblSenha);
			pnlForm.add(txtSenha);
		}
		return pnlForm;
	}
	private JPanel getPnlBotoes() {
		if (pnlBotoes == null) {
			pnlBotoes = new JPanel();
			
			pnlBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			btnEntrar = new JButton("Entrar");
			btnCancelar = new JButton("Cancelar");
			
			pnlBotoes.add(btnEntrar);
			pnlBotoes.add(btnCancelar);
		}
		return pnlBotoes;
	}
	
	private void entrar(){

		UsuarioDao dao = new UsuarioDao();
		UsuarioModel usuario;
		
		if (StringUtils.trim(txtUsuario.getText()).isEmpty()) {
			JOptionPane.showMessageDialog(this, "Campo [Usuário] não informado!");
			return;
		}
		
		String senha = new String(txtSenha.getPassword());
		
		if (StringUtils.trim(senha).isEmpty()) {
			JOptionPane.showMessageDialog(this, "Campo [Senha] não informado!");
			return;
		}
		
		
		try {
			usuario = dao.consultarUsuarioPorLogin(txtUsuario.getText().trim());

			if(dao.consultarUsuarioPorLogin(txtUsuario.getText().trim()) == null) {
				JOptionPane.showMessageDialog(this, "Usuario [" + txtUsuario.getText() + "] não encontrado!",
						"Atenção", JOptionPane.WARNING_MESSAGE);
				return;		
			}
			
		} catch (BancoDadosException ex) {
			
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			LOG.error(ex.getMessage(), ex);
			return;
		}
		
		if(!senha.contentEquals(usuario.getSenha())) {
			JOptionPane.showMessageDialog(this, "Senha inválida do Usuario",
				"Atenção", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			SwingUtilities.invokeLater(() -> {
				FormPrincipal formPrincipal = new FormPrincipal();
				formPrincipal.setVisible(true);
				
			});
			
		}finally {
			this.setVisible(false);
			this.dispose();
		
		}
	}
	
	
}
