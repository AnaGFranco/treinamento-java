package com.avanade.arquivo;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.avanade.arquivo.componentes.StringBuilderLines;
import com.avanade.arquivo.exception.BancoDadosException;
import com.avanade.arquivo.model.PessoaModel;
import com.avanade.arquivo.dao.PessoaDao;

public class ExportaDadosFormatado extends AbstractExportarAquivo {

	/**
	 * @return Extensão do arquivo
	 */
	@Override
	protected String getExtensaoArquivo() {
		return "txt";
	}

	/**
	 * Add dados na lista
	 * 
	 * @return list - conteudo que sera inserido no arquivo
	 */
	
	/* Lista de conteudo sem a utilização de banco de  dados
	 * 
	private List<PessoaData> gerarDadosPessoa() {
	    List<PessoaData> list = new ArrayList<PessoaData>();
	    for (int i = 1; i <= 100; i++) {
	        PessoaData pessoa = new PessoaData();
	        pessoa.setId(i);
	        pessoa.setNome("Pessoa Número " + i);
	        pessoa.setEndereco("Rua da pessoa " + i);
	        list.add(pessoa);
	    }
	    return list;
	} */
	
	/**
	 * Criar documento
	 * 
	 * @return conteúdo do arquivo
	 * @throws BancoDadosException 
	 */
	
	protected StringBuilder getConteudo() throws BancoDadosException {
		StringBuilderLines sbl = new StringBuilderLines();
		PessoaDao p = new PessoaDao();
		
		List<PessoaModel> lst = p.listarPessoas();
		for (PessoaModel pessoa : lst) {
			int id = pessoa.getCodigo();
			String padId = StringUtils.leftPad(String.valueOf(id), 5, '0');

			String nome = pessoa.getNome();
			String padNome = StringUtils.rightPad(nome, 30, ' ');

			String endereco = pessoa.getEndereco();
			String padEndereco = StringUtils.rightPad(endereco, 50, ' ');

			String linha = padId;
			linha += padNome;
			linha += padEndereco;

			sbl.appendLine(linha);
		}
		return sbl.getContent();
	}
}
