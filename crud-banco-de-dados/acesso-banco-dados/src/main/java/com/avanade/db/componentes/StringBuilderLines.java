package com.avanade.db.componentes;

/**
 * Class usada para herdar funcionalidades de StringBuilder
 * @author ana siqueira franco
 *
 */
public class StringBuilderLines {

	public static void main(String[] args) {
		StringBuilderLines sbl = new StringBuilderLines();
		sbl.getContent().append("abc");
	}
	
	/**
	 * Passa a informação da class StringBuilder para a var
	 * pois StringBuilder nao pode ser extends
	 */
	private StringBuilder content;
	
	/**
	 * Valida valor e pula linhas para facilitar na criação do xml
	 * @param str
	 */
	public void appendLine(String str) {
		if (str == null) {
			return;
		}
		
		getContent().append(str);
		getContent().append("\n");
	}
	/**
	 * Implementa o metodo appendLine para validar e pular linha
	 * @param i
	 */
	public void appendLine(int i) {
		appendLine(String.valueOf(i));
	}
	
	/**
	 *  retorna funcinalidades de StringBuilder
	 * @return
	 */
	public StringBuilder getContent() {
		if (content == null) {
			content = new StringBuilder();
		}
		return content;
	}
}
