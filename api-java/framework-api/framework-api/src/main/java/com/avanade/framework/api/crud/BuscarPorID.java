package com.avanade.framework.api.crud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.PessoaDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;

public class BuscarPorID extends AbstractCrud {

	private static final long serialVersionUID = -413685189142313413L;
	private static final Logger LOG = LoggerFactory.getLogger(BuscarPorID.class);


	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String strCodigo = req.getParameter("codigo");
		if (StringUtils.trimToEmpty(strCodigo).isEmpty()) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Codigo para consulta nao informado");
			return;
		}

		if (!StringUtils.isNumeric(strCodigo)) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Codigo informado deve ser um numero valido");
			return;
		}

		Integer codigo = Integer.valueOf(strCodigo);

		PessoaDao dao = new PessoaDao();
		PessoaModel pessoa;
		try {
			pessoa = dao.consultaPorCodigo(codigo);
		} catch (BancoDadosException ex) {
			LOG.error(ex.getMessage(), ex);
			return;
		}

		String json = objetoEmJson(pessoa);
		retornoJson(resp, json);

		return;

	}

}
