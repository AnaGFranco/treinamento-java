package com.avanade.arquivo;

import com.avanade.arquivo.componentes.StringBuilderLines;

/**
 * Class responsavel em exportar o xml
 * 
 * @author ana siqueira franco
 */
public class ExportaXML extends AbstractExportarAquivo {

	/**
	 * @return Extens�o do arquivo
	 */
	@Override
	protected String getExtensaoArquivo() {
		return "xml";
	}

	@Override

	/**
	 * Criar documento
	 * 
	 * @return conteudo do arquivo
	 */
	protected StringBuilder getConteudo() {
		StringBuilderLines sbl = new StringBuilderLines();
		sbl.appendLine("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		sbl.appendLine("<receita nome=\"p�o\" tempo_de_preparo=\"5 minutos\" tempo_de_cozimento=\"1 hora\">");
		sbl.appendLine("    <titulo>P�o simples</titulo>");
		sbl.appendLine("    <ingredientes>");
		sbl.appendLine("        <ingrediente quantidade=\"3\" unidade=\"x�caras\">Farinha de Trigo</ingrediente>");
		sbl.appendLine("    </ingredientes>");
		sbl.appendLine("</receita>");

		return sbl.getContent();
	}

}
