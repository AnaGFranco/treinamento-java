package com.avanade.db.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfiguracaoDbGlobal {

	private static final Logger LOG = LoggerFactory.getLogger(ConfiguracaoDbGlobal.class);
	
	private static final String ARQUIVO_CONFIG = "./config.properties";
	
	private static final String MODELO_EXIBE_CHAVE = "  {} = {} ";
	private static final int TAM_LINHA_LOG = 15;
	
	private static final String CHAVE_CONFIG_DB_DRIVER = "db.driver";
	private static final String CHAVE_CONFIG_DB_URL = "db.url";
	private static final String CHAVE_CONFIG_DB_USUARIO = "db.usuario";
	private static final String CHAVE_CONFIG_DB_SENHA = "db.senha";
	
	private static String dbDriver;
	private static String dbURL;
	private static String dbUsuario;
	private static String dbSenha;
	
	/**
	 * O contrutor privado impede que seja criada 
	 * uma instância da classe
	 */
	private ConfiguracaoDbGlobal() {
	}
	
	public static void carregarConfiguracao() throws IOException {
		
		LOG.info("Carregando arquivo de configuracão: {}", ARQUIVO_CONFIG);
		
		File arquivoConfig = new File(ARQUIVO_CONFIG);
		if (!arquivoConfig.exists()) {
			throw new IOException("Arquivo [" + arquivoConfig.getAbsolutePath() + "] não encontrado");
		}
		
		LOG.info("Arquivo de configuracão {} encontrado", ARQUIVO_CONFIG);
		
		Properties config = new Properties();
		config.load(new FileReader(arquivoConfig));
		
		dbDriver = parametroPorChave(config, CHAVE_CONFIG_DB_DRIVER);
		dbURL = parametroPorChave(config, CHAVE_CONFIG_DB_URL);
		dbUsuario = parametroPorChave(config, CHAVE_CONFIG_DB_USUARIO);
		dbSenha = config.getProperty(CHAVE_CONFIG_DB_SENHA);
		
		LOG.info("Configurações carregadas com sucesso");
		LOG.info(StringUtils.leftPad("", TAM_LINHA_LOG, "="));
		LOG.info(MODELO_EXIBE_CHAVE, CHAVE_CONFIG_DB_DRIVER, dbDriver);
		LOG.info(MODELO_EXIBE_CHAVE, CHAVE_CONFIG_DB_URL, dbURL);
		LOG.info(MODELO_EXIBE_CHAVE, CHAVE_CONFIG_DB_USUARIO, dbUsuario);
		LOG.info(MODELO_EXIBE_CHAVE, CHAVE_CONFIG_DB_SENHA, dbSenha);
		LOG.info(StringUtils.leftPad("", TAM_LINHA_LOG, "="));
	}
	
	private static String parametroPorChave(Properties config, String chave) {
		
		String valor = config.getProperty(chave);
		if (StringUtils.trimToEmpty(valor).isEmpty()) {
			throw new RuntimeException("Chave de configuração não informada ou vazia [" + chave + "]");
		}
		
		return valor;
		
	}
	
	public static String getDbDriver() {
		return dbDriver;
	}
	
	public static String getDbURL() {
		return dbURL;
	}
	
	public static String getDbUsuario() {
		return dbUsuario;
	}
	
	public static String getDbSenha() {
		return dbSenha;
	}
	
}
