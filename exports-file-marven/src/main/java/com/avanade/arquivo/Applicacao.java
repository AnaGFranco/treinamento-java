package com.avanade.arquivo;

import java.io.IOException;

public class Applicacao {

	/**
	 * main - executa a exportação.
	 */

	public static void main(String[] args) {

		AbstractExportarAquivo exporta = new ExportaDadosFormatado();

		System.out.println("Iniciando exportação de arquivo...");
		/**
		 * indica caminho a ser exportado o documento, e realizar tratamneto de exceção
		 * para o usuario final
		 */
		try {
			exporta.executar("D:\\Development\\treinamento-java\\exporta-arquivo\\temp", "receita");
			System.out.println("Exportação concluída com sucesso!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
