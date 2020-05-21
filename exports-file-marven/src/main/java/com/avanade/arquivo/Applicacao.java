package com.avanade.arquivo;

import java.io.IOException;

public class Applicacao {

	/**
	 * main - executa a exporta��o.
	 */

	public static void main(String[] args) {

		AbstractExportarAquivo exporta = new ExportaDadosFormatado();

		System.out.println("Iniciando exporta��o de arquivo...");
		/**
		 * indica caminho a ser exportado o documento, e realizar tratamneto de exce��o
		 * para o usuario final
		 */
		try {
			exporta.executar("D:\\Development\\treinamento-java\\exporta-arquivo\\temp", "receita");
			System.out.println("Exporta��o conclu�da com sucesso!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
