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
	 *  Classe abstrata que efetua o controle da criação do arquivo
	 */
	private enum TipoString {PARAMETRO, METODO}
	
	/**
     * Conteúdo que será salvo no arquivo
     *
     * @return Um StringBuilder com o conteúdo do arquivo
     */
	
	protected abstract String getExtensaoArquivo();
	
	/**
	 * Construção do conteudo contido no arquivo
	 * 
	 * @return conteudo do aruivo
	 * @throws BancoDadosException 
	 */
	protected abstract StringBuilder getConteudo() throws BancoDadosException;
	
	/**
	 * Implenta o metodo validarString para verificar se os valores do parametro são vazios ou nulos
	 * @param valor
	 * @param descricao
	 */
	
	private void validarStringParametro(String valor, String descricao) {
		validarString(valor, TipoString.PARAMETRO, descricao);
	}
	
	/**
	 * Valida se o StringBuilder está null, diferente de null converte em string 
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
	 * Implenta o método validarString para verificar se os valores do metodo são vazios ou nulos
	 * @param valor
	 * @param descricao
	 */
	
	private void validarStringMetodo(String valor, String descricao) {
		validarString(valor, TipoString.METODO, descricao);
	}
	
	/**
	 * Verifica se o valor da string passada é vazio ou null e trata a exibição do erro apresentado.
	 * @param valor
	 * @param tipo
	 * @param descricao 
	 */
	private void validarString(String valor, TipoString tipo, String descricao) {
		
		String descricaoTipo = TipoString.PARAMETRO.equals(tipo) ? "Parâmetro" : "Método";
		
		if (StringUtils.isBlank(valor)) {
			throw new RuntimeException("O " + descricaoTipo + " de [" + descricao + "] não pode ser nulo ou vazio");
		}
	}
	
	/**
	 * Implementa a validação dos valores de parametros e metodos 
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
	 * Verifica se os valores estão corretos e concatena
	 * @param caminho
	 * @param nome
	 * @throws IOException
	 * @throws BancoDadosException 
	 */
	
	public void executar(String caminho, String nome) throws IOException, BancoDadosException {
		validarValores(caminho, nome);
		
		File dir = new File(caminho);
		if (!dir.exists()) {
			throw new FileNotFoundException("Caminho informado não encontrado [" + caminho + "]");
		}
		
		if (!dir.canWrite()) {
			throw new FileNotFoundException("Sem permissão de escrita no caminho informado [" + caminho + "]");
		}
		
		// Exemplo de concatenação de string utilizando um StringBuilder
		StringBuilder sbCaminho = new StringBuilder();
		sbCaminho.append(caminho);
		sbCaminho.append(File.separator);
		sbCaminho.append(nome);
		sbCaminho.append(".");
		sbCaminho.append(getExtensaoArquivo());
		
		String caminhoArquivo = sbCaminho.toString();
		File arquivo = new File(caminhoArquivo);
		
		if (arquivo.exists()) {
			throw new IOException("Aquivo informado para geração já existe [" + caminhoArquivo + "]");
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
		writer.write(getConteudo().toString());
		writer.close();
	}
	
	
}
