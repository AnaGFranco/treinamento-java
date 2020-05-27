package com.avanade.crud.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;
import com.google.gson.Gson;

public class FormPrincipal extends JFrame {

	private static final long serialVersionUID = -9118327248898866442L;
	private static final Logger LOG = LoggerFactory.getLogger(FormPrincipal.class);

	private JPanel pnlTitulo;

	private JPanel pnlForm;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTextField txtEndereco;

	private JPanel pnlBotoes;
	private JButton btnSalvar;
	private JButton btnListar;

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

		btnSalvar.addActionListener(l -> {
			try {
				salvar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		btnListar.addActionListener(l -> {
			try {
				listar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

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
			pnlForm.setLayout(new GridLayout(3, 2, 5, 5)); // Cria colunas e linhas

			JLabel lblCodigo = new JLabel("  Codigo:");
			txtCodigo = new JTextField();

			JLabel lblNome = new JLabel("  Nome:");
			txtNome = new JTextField();

			JLabel lblEndereco = new JLabel("  Endereço:");
			txtEndereco = new JTextField();

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
			btnListar = new JButton("Listar");

			pnlBotoes.add(btnSalvar);
			pnlBotoes.add(btnListar);
		}
		return pnlBotoes;
	}

	private void salvar() throws Exception {
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

		try {
			sendPost(pessoa);

		} catch (BancoDadosException ex) {

			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			LOG.error(ex.getMessage(), ex);
			return;
			
		} finally {
			close();
		}

		JOptionPane.showMessageDialog(this, "Pessoa [" + pessoa.getNome() + "] salvar com sucesso");
		txtCodigo.setText("");
		txtNome.setText("");
		txtEndereco.setText("");

	}
	
	private void listar() throws Exception {
	
		try {
			SwingUtilities.invokeLater(() -> {
				
                List<PessoaModel> dados = new ArrayList<PessoaModel>();            
                
				PessoaTabelaModel model = new PessoaTabelaModel(dados);
	                JTable table = new JTable(model) {
	                 
						private static final long serialVersionUID = 1L;

						@Override
	                    public Dimension getPreferredScrollableViewportSize() {
	                        return new Dimension(300, 100);
	                                           
	                    }
	                };
	                JOptionPane.showMessageDialog(null, new JScrollPane(table));
			});

			
		}finally {
			
			this.dispose();
		
		}

	}


	private void fecharJanela() {
		// Exibe mensagem verificando se deve fechar
		int opcao = JOptionPane.showConfirmDialog(this, "Deja realmente sair?", "Confirmação",
				JOptionPane.YES_NO_OPTION);

		// Se selecionou sim, fecha a janela principal
		if (opcao == JOptionPane.YES_OPTION) {
			this.setVisible(false);
			this.dispose();
		}

	}

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	private void close() throws IOException {
		httpClient.close();
	}

	private void sendPost(PessoaModel pessoa) throws Exception {

		HttpPost post = new HttpPost("http://localhost:8080/framework-api/cad/pessoa?acao=Salvar");
		
		Gson gson = new Gson();
		String userJSONString = gson.toJson(pessoa);
		
		
		StringEntity entity = new StringEntity(userJSONString);

		entity.setContentType("application/json");

		post.setEntity(entity);	
		
		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");


		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println(EntityUtils.toString(response.getEntity()));
			
		}catch(Exception ex){
			
			System.out.println(ex);
			
		}
	}

	private void sendGet() throws Exception {

		HttpGet request = new HttpGet("http://localhost:8080/framework-api/cad/pessoa");

		// add request headers
//	        request.addHeader("custom-key", "mkyong");
//	        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status
			System.out.println(response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);

			if (entity != null) {
				// return it as a String
				String result = EntityUtils.toString(entity);
				System.out.println(result);
			}

		}
	}
}
