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
import com.avanade.framework.api.AbstractServletAPI;

public class Listar extends AbstractServletAPI {

	private static final long serialVersionUID = -5338009100724020722L;
	private static final Logger LOG = LoggerFactory.getLogger(Listar.class);

	private String json;

	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		TipoAcao tipoAcao = recuperarTipoAcao(req);

		PessoaDao dao = new PessoaDao();
		List<PessoaModel> lista;

		try {

			switch (tipoAcao) {
			case LISTAR_TODOS:

				lista = dao.listarPessoas();

				json = objetoEmJson(lista);
				retornoJson(resp, json);

				return;
			case BUSCA_POR_ID:

				PessoaModel pessoa;
					
					pessoa = dao.consultaPorCodigo(validaCodigo(req, resp));
			
				json = objetoEmJson(pessoa);
				retornoJson(resp, json);

				return;				
			default:
				break;
			}
			
		} catch (BancoDadosException ex) {
			LOG.error(ex.getMessage(), ex);
			return;
		}

	}

}
