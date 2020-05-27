package com.avanade.framework.api.crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.PessoaDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;

public class ListarTodos extends AbstractCrud {

	private static final long serialVersionUID = -5338009100724020722L;
	private static final Logger LOG = LoggerFactory.getLogger(ListarTodos.class);

	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PessoaDao dao = new PessoaDao();
		List<PessoaModel> lista;
		try {
			lista = dao.listarPessoas();
		} catch (BancoDadosException ex) {
			LOG.error(ex.getMessage(), ex);
			return;
		}

		String json = objetoEmJson(lista);
		retornoJson(resp, json);

		return;

	}
}
