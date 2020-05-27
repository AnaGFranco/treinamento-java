package com.avanade.framework.listener;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.config.ConfiguracaoDbGlobal;
import com.avanade.db.dao.GerenciaConexao;

public class BancoDadosListener implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(BancoDadosListener.class);
	
	private static final String CHAVE_JDBC_DRIVER = "jdbc-driver";
	private static final String CHAVE_JDBC_URL = "jdbc-url";
	private static final String CHAVE_JDBC_USUARIO = "jdbc-usuario";
	private static final String CHAVE_JDBC_SENHA = "jdbc-senha";
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.info("Parametros conexão com o banco de dados");
		
		ServletContext context = sce.getServletContext();
		
		// Recupera valores de configuração
		String jdbcDriver = context.getInitParameter(CHAVE_JDBC_DRIVER);
		String jdbcUrl = context.getInitParameter(CHAVE_JDBC_URL);
		String jdbcUsuario = context.getInitParameter(CHAVE_JDBC_USUARIO);
		String jdbcSenha = context.getInitParameter(CHAVE_JDBC_SENHA);
		
		LOG.info("Dados para conexão");
		LOG.info("  - Driver  = {}", jdbcDriver);
		LOG.info("  - URL     = {}", jdbcUrl);
		LOG.info("  - Usuario = {}", jdbcUsuario);
		LOG.info("  - Senha   = {}", StringUtils.trimToEmpty(jdbcSenha).isEmpty());
		LOG.info("==================");
		
		ConfiguracaoDbGlobal.setDbDriver(jdbcDriver);
		ConfiguracaoDbGlobal.setDbURL(jdbcUrl);
		ConfiguracaoDbGlobal.setDbUsuario(jdbcUsuario);
		ConfiguracaoDbGlobal.setDbSenha(jdbcSenha);
		
		try {
			GerenciaConexao.openConnection();
		} catch (ClassNotFoundException | SQLException ex) {
			LOG.error("Falha ao conectar com o banco de dados", ex);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	
	
}
