package com.avanade.framework.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.avanade.framework.api.crud.AbstractCrud;
import com.avanade.framework.api.crud.BuscarPorID;
import com.avanade.framework.api.crud.ListarTodos;
import com.avanade.framework.api.crud.Salvar;

public class PessoaAPI extends AbstractCrud {

	private static final long serialVersionUID = -6324241741847409418L;

	private static final Map<TipoAcao, AbstractCrud> LISTA_OPERACOES = new HashMap<TipoAcao, AbstractCrud>();

	static {
		// Inicializar lista de operações
		LISTA_OPERACOES.put(TipoAcao.LISTAR_TODOS, new ListarTodos());
		LISTA_OPERACOES.put(TipoAcao.BUSCA_POR_ID, new BuscarPorID());
		LISTA_OPERACOES.put(TipoAcao.SALVAR, new Salvar());

	}
	
	protected void executarCrud(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbstractCrud crud = LISTA_OPERACOES.get(tipoAcao);
		crud.executar(req, resp);
		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		executarCrud(req,resp);
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		executarCrud(req,resp);
			
	}



	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		tipoAcao = recuperarTipoAcao(req);
		if (isGet(req) && !acoesGet.contains(tipoAcao)) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Acao informada nao ser invocada pelo metodo GET");
			return;
		}

		if (tipoAcao.equals(TipoAcao.INVALIDA)) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Acao invalida");
			return;
		}

		super.service(req, resp);
		
	}

}
