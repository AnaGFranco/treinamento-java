package com.avanade.framework.api.crud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.PessoaDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;
import com.avanade.framework.api.AbstractServletAPI;

public class Exclusao extends AbstractServletAPI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exclusao.class);

	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer codigo = validaCodigo(req, resp);

		PessoaDao dao = new PessoaDao();
		PessoaModel pessoa;
		
		int returnoCodigo;
		try {
			pessoa = dao.consultaPorCodigo(codigo);
			if (pessoa != null) {
				returnoCodigo = dao.excluir(codigo);
				mensagemJson("Exclusao efetuado com sucesso do codigo=" + returnoCodigo);
			}

		} catch (BancoDadosException ex) {
			LOG.error(ex.getMessage(), ex);
			return;
		}

		return;		
	}
}