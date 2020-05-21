package com.avanade.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.config.ConfiguracaoDbGlobal;
import com.avanade.db.exception.ConexaoNaoIniciadaException;

public final class GerenciaConexao {

	private static final Logger LOG = LoggerFactory.getLogger(GerenciaConexao.class);

	private static Connection connection;
	
	/**
	 * O contrutor privado impede que seja criada 
	 * uma instância da classe
	 */
	private GerenciaConexao() {
	}
	
	public static Connection getConnection() {
		if (connection == null) {
			throw new ConexaoNaoIniciadaException("Conexão com banco não foi iniciada. "
					+ "Verifique se o método [openConnection()] foi executado");
		}
		
		return connection;
	}
	
	public static void openConnection() throws ClassNotFoundException, SQLException {
		
		LOG.info("Iniciando conexão HSQLDB");
		Class.forName(ConfiguracaoDbGlobal.getDbDriver());
		connection = DriverManager.getConnection(ConfiguracaoDbGlobal.getDbURL(), 
				ConfiguracaoDbGlobal.getDbUsuario(), ConfiguracaoDbGlobal.getDbSenha());

		String status = (connection.isClosed()) ? "NOK" : "OK";
		LOG.info("Conexão efetuada com sucesso [status = {}]", status);
		
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
