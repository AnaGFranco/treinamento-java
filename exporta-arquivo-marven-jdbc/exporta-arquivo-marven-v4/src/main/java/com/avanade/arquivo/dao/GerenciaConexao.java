package com.avanade.arquivo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.arquivo.config.ConfiguracaoGlobal;
import com.avanade.arquivo.exception.ConexaoNaoIniciadaException;

public final class GerenciaConexao {

	private static final Logger LOG = LoggerFactory.getLogger(GerenciaConexao.class);

	private static Connection connection;
	
	/**
	 * O contrutor privado impede que seja criada 
	 * uma inst�ncia da classe
	 */
	private GerenciaConexao() {
	}
	
	public static Connection getConnection() {
		if (connection == null) {
			throw new ConexaoNaoIniciadaException("Conex�o com banco n�o foi iniciada. "
					+ "Verifique se o m�todo [openConnection()] foi executado");
		}
		
		return connection;
	}
	
	public static void openConnection() throws Exception {
		
		LOG.info("Iniciando conex�o HSQLDB");
		Class.forName(ConfiguracaoGlobal.getDbDriver());
		connection = DriverManager.getConnection(ConfiguracaoGlobal.getDbURL(), 
				ConfiguracaoGlobal.getDbUsuario(), ConfiguracaoGlobal.getDbSenha());

		String status = (connection.isClosed()) ? "NOK" : "OK";
		LOG.info("Conex�o efetuada com sucesso [status = {}]", status);
		
	}
	
	public static boolean isActive() {
		if (connection == null) {
			return false;
		}
		
		try {
			return !connection.isClosed();
		} catch (SQLException ex) {
			return false;
		}
		
	}
	
}
