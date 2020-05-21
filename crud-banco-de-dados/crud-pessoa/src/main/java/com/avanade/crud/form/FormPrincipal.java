package com.avanade.crud.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.PessoaDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;

public class FormPrincipal extends JFrame{
	
	private static final long serialVersionUID = -9118327248898866442L;
	private static final Logger LOG = LoggerFactory.getLogger(FormPrincipal.class);

	
	private JPanel pnlTitulo;

	private JPanel pnlForm;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTextField txtEndereco;
	
	private JPanel pnlBotoes;
	private JButton btnSalvar;
	

	
	public FormPrincipal() {
		super();
		this.iniciar();	
	}
	
	public void iniciar() {
		
		this.setTitle("Crud Pessoa");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		this.getContentPane().add(getPnlTitulo(), BorderLayout.NORTH);
		this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
		this.getContentPane().add(getPnlBotoes(), BorderLayout.SOUTH);
		this.pack();
		
		this.setLocationRelativeTo(null); // centralizar tela
		this.incluirEventos();
	}
	
	
	private void incluirEventos() {
	
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent ev) {
				fecharJanela();
			}
			
		});
		
		btnSalvar.addActionListener(l-> this.salvar());
		
	}
	public JPanel getPnlTitulo() {
	if (pnlTitulo == null) {
			
			pnlTitulo = new JPanel();
			pnlTitulo.setLayout(new FlowLayout());		
			
			JLabel lblTitulo = new JLabel("Cadastro");
			
			pnlTitulo.add(lblTitulo);
		}
		return pnlTitulo;
	}
	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel();
			pnlForm.setLayout(new GridLayout(3, 2, 5, 5)); //Cria colunas e linhas
			
			JLabel lblCodigo = new JLabel("  Codigo:"); 
			txtCodigo = new JTextField();
			
			JLabel lblNome = new JLabel("  Nome:"); 
			txtNome = new JTextField();
			
			JLabel lblEndereco = new JLabel("  Endereço:"); 
			txtEndereco = new JTextField(); // mostrar senha mascarada
			
			pnlForm.add(lblCodigo);
			pnlForm.add(txtCodigo);
			
			pnlForm.add(lblNome);
			pnlForm.add(txtNome);
			
			pnlForm.add(lblEndereco);
			pnlForm.add(txtEndereco);
		}
		return pnlForm;
	}
	
	public JPanel getPnlBotoes() {
		if (pnlBotoes == null) {
			pnlBotoes = new JPanel();
			
			pnlBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			btnSalvar = new JButton("Salvar");
			
			pnlBotoes.add(btnSalvar);
		}
		return pnlBotoes;
	}
	

	
	private void salvar() {
		if (StringUtils.trim(txtCodigo.getText()).isEmpty()) {
		JOptionPane.showMessageDialog(this, "Campo [Código] não informado!");
		return;
		}
		
		Integer codigo;
		try {
			codigo = Integer.valueOf(txtCodigo.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Campo [Código] não é um número válido!");
			return;
		}
		
		if (StringUtils.trim(txtNome.getText()).isEmpty()) {
			JOptionPane.showMessageDialog(this, "Campo [Nome] não informado!");
			return;
		}
		
		if (StringUtils.trim(txtEndereco.getText()).isEmpty()) {
			JOptionPane.showMessageDialog(this, "Campo [Endereço] não informado!");
			return;
		}
		PessoaModel pessoa = new PessoaModel();
		pessoa.setCodigo(codigo);
		pessoa.setNome(txtNome.getText());
		pessoa.setEndereco(txtEndereco.getText());
		
		PessoaDao dao = new PessoaDao();
		try {
			dao.salvar(pessoa);
			
		} catch (BancoDadosException ex) {
				
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				LOG.error(ex.getMessage(), ex);
				return;
		}
			
		JOptionPane.showMessageDialog(this, "Pessoa [" + pessoa.getNome() + "] salvar com sucesso");
		txtCodigo.setText("");
		txtNome.setText("");
		txtEndereco.setText("");

	
	}
	
	private void fecharJanela() {
		//Exibe mensagem verificando se deve fechar
		int opcao = JOptionPane.showConfirmDialog(this, "Deja realmente sair?","Confirmação", JOptionPane.YES_NO_OPTION);
		
		//Se selecionou sim, fecha a janela principal
		if(opcao == JOptionPane.YES_OPTION){
			this.setVisible(false);
			this.dispose();
		}
		
	}
		
	
}
