package com.avanade.arquivo;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.arquivo.config.ConfiguracaoGlobal;
import com.avanade.arquivo.dao.GerenciaConexao;
import com.avanade.arquivo.exception.BancoDadosException;

public class Applicacao {

	private static final Logger LOG = LoggerFactory.getLogger(Applicacao.class);
	private static final int COD_ERRO_APP = 10;

	/*
	 * Metodo principal chama o metodo executar
	 */
	public static void main(String[] args) throws BancoDadosException {
		

		LOG.info("Iniciando aplicação JDBC...");
		Applicacao aplicacao = new Applicacao();
		aplicacao.iniciar();
	
	}

	private void iniciar() throws BancoDadosException {

		try {
			// Carregar as configurações
			ConfiguracaoGlobal.carregarConfiguracao();
		} catch (Exception ex) {
			LOG.error("Falha ao carregar configurações", ex);
			System.exit(COD_ERRO_APP);
		}

		try {
			// Conectando com o banco de dados
			GerenciaConexao.openConnection();
		} catch (Exception ex) {
			LOG.error("Falha ao conectar com o banco de dados", ex);
			System.exit(COD_ERRO_APP);

		}
		consultarPessoa();
	}
	
	private void consultarPessoa() throws BancoDadosException {
		AbstractExportarAquivo exporta = new ExportaDadosFormatado();
		
		try {
			// Metodo executar passa o parametro caminho e nome do arquivo
			exporta.executar("D:\\Development\\treinamento-java\\exporta-arquivo-marven-v4\\temp", "arquivo");
			System.out.println("Exportação concluída com sucesso!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	
}
