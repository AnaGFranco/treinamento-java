package com.avanade.arquivo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.avanade.arquivo.exception.BancoDadosException;


public abstract class AbstractExportarAquivo {

	/**
	 *  Classe abstrata que efetua o controle da cria��o do arquivo
	 */
	private enum TipoString {PARAMETRO, METODO}
	
	/**
     * Conte�do que ser� salvo no arquivo
     *
     * @return Um StringBuilder com o conte�do do arquivo
     */
	
	protected abstract String getExtensaoArquivo();
	
	/**
	 * Constru��o do conteudo contido no arquivo
	 * 
	 * @return conteudo do aruivo
	 * @throws BancoDadosException 
	 */
	protected abstract StringBuilder getConteudo() throws BancoDadosException;
	
	/**
	 * Implenta o metodo validarString para verificar se os valores do parametro s�o vazios ou nulos
	 * @param valor
	 * @param descricao
	 */
	
	private void validarStringParametro(String valor, String descricao) {
		validarString(valor, TipoString.PARAMETRO, descricao);
	}
	
	/**
	 * Valida se o StringBuilder est� null, diferente de null converte em string 
	 * e implementa validarStringMetodo(strValor, descricao) para fazer o tratamento de erro.
	 * 
	 * @param valor
	 * @param descricao
	 */
	private void validarStringMetodo(StringBuilder valor, String descricao) {
		String strValor = null;
		if (StringUtils.isNotBlank(valor)) {
			strValor = valor.toString();
		}

		validarStringMetodo(strValor, descricao);
	}
	
	/**
	 * Implenta o m�todo validarString para verificar se os valores do metodo s�o vazios ou nulos
	 * @param valor
	 * @param descricao
	 */
	
	private void validarStringMetodo(String valor, String descricao) {
		validarString(valor, TipoString.METODO, descricao);
	}
	
	/**
	 * Verifica se o valor da string passada � vazio ou null e trata a exibi��o do erro apresentado.
	 * @param valor
	 * @param tipo
	 * @param descricao 
	 */
	private void validarString(String valor, TipoString tipo, String descricao) {
		
		String descricaoTipo = TipoString.PARAMETRO.equals(tipo) ? "Par�metro" : "M�todo";
		
		if (StringUtils.isBlank(valor)) {
			throw new RuntimeException("O " + descricaoTipo + " de [" + descricao + "] n�o pode ser nulo ou vazio");
		}
	}
	
	/**
	 * Implementa a valida��o dos valores de parametros e metodos 
	 * @param caminho
	 * @param nome
	 * @throws IOException
	 * @throws BancoDadosException 
	 */
	
	private void validarValores(String caminho, String nome) throws IOException, BancoDadosException {
		
		validarStringParametro(caminho, "caminho");
		validarStringParametro(nome, "nome");
		validarStringMetodo(getExtensaoArquivo(), "getExtensaoArquivo()");
		validarStringMetodo(getConteudo(), "getExtensaoArquivo()");	
	}
	
	/**
	 * Verifica se os valores est�o corretos e concatena
	 * @param caminho
	 * @param nome
	 * @throws IOException
	 * @throws BancoDadosException 
	 */
	
	public void executar(String caminho, String nome) throws IOException, BancoDadosException {
		validarValores(caminho, nome);
		
		File dir = new File(caminho);
		if (!dir.exists()) {
			throw new FileNotFoundException("Caminho informado n�o encontrado [" + caminho + "]");
		}
		
		if (!dir.canWrite()) {
			throw new FileNotFoundException("Sem permiss�o de escrita no caminho informado [" + caminho + "]");
		}
		
		// Exemplo de concatena��o de string utilizando um StringBuilder
		StringBuilder sbCaminho = new StringBuilder();
		sbCaminho.append(caminho);
		sbCaminho.append(File.separator);
		sbCaminho.append(nome);
		sbCaminho.append(".");
		sbCaminho.append(getExtensaoArquivo());
		
		String caminhoArquivo = sbCaminho.toString();
		File arquivo = new File(caminhoArquivo);
		
		if (arquivo.exists()) {
			throw new IOException("Aquivo informado para gera��o j� existe [" + caminhoArquivo + "]");
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
		writer.write(getConteudo().toString());
		writer.close();
	}
	
	
}
