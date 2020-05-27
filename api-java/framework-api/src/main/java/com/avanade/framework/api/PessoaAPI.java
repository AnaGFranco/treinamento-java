package com.avanade.framework.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.avanade.framework.api.crud.Exclusao;
import com.avanade.framework.api.crud.Listar;
import com.avanade.framework.api.crud.Salvar;

public class PessoaAPI extends AbstractServletAPI {

	private static final long serialVersionUID = 4750350472781019047L;
	
	protected static List<TipoAcao> acoesGet;

		
	private static final Map<TipoAcao, AbstractServletAPI> LISTA_OPERACOES = new HashMap<TipoAcao, AbstractServletAPI>();

	static {
		// Inicializar lista de operações
		LISTA_OPERACOES.put(TipoAcao.LISTAR_TODOS, new Listar());
		LISTA_OPERACOES.put(TipoAcao.BUSCA_POR_ID, new Listar());
		LISTA_OPERACOES.put(TipoAcao.SALVAR, new Salvar());
		LISTA_OPERACOES.put(TipoAcao.EXCLUSAO, new Exclusao());
		
		acoesGet = new ArrayList<PessoaAPI.TipoAcao>();
		acoesGet.add(TipoAcao.LISTAR_TODOS);
		acoesGet.add(TipoAcao.BUSCA_POR_ID);
	}
	
	
	private void executarCrud(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AbstractServletAPI crud = LISTA_OPERACOES.get(tipoAcao);
		crud.executar(req, resp);		
	}
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		executarCrud(req, resp);

	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		executarCrud(req, resp);
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
