package com.avanade.crud;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.crud.form.FormLogin;
import com.avanade.db.CarregarDb;

public class AplicacaoCrud {

	private static final Logger LOG = LoggerFactory.getLogger(AplicacaoCrud.class);
	private static final int COD_ERRO = 10;
	
	public static void main(String[] args) {
		
		LOG.info("Iniciano aplicação CRUD");
		
		AplicacaoCrud aplicacao = new AplicacaoCrud();
		aplicacao.iniciar();
		
	}
	private void iniciar() {
		try {
			CarregarDb.iniciar();
		} catch (IOException ex) {
			LOG.error("Falha ao carregar arquivo de configuração ", ex);
			System.exit(COD_ERRO);
		} catch (ClassNotFoundException | SQLException ex) {
			LOG.error("Error ao conectar com o banco de dados ",ex);
			System.exit(COD_ERRO);
		}
		
		System.out.println("===========ok============");
		
		SwingUtilities.invokeLater(() -> {
			FormLogin form = new FormLogin();
			
			form.setVisible(true);
			
//			FormPrincipal formPrincipal = new FormPrincipal();
//			formPrincipal.setVisible(true);
			
		});
	}
}
