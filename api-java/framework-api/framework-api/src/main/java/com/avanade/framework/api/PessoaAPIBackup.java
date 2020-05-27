package com.avanade.framework.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.PessoaDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PessoaAPIBackup extends AbstractServletAPI {

	private static final long serialVersionUID = -6324241741847409418L;
	private static final Logger LOG = LoggerFactory.getLogger(PessoaAPIBackup.class);
	
	private static List<TipoAcao> acoesGet;
	
	private enum TipoAcao {LISTAR_TODOS, BUSCA_POR_ID, SALVAR, EXCLUSAO, INVALIDA}

	static {
		acoesGet = new ArrayList<PessoaAPIBackup.TipoAcao>();
		acoesGet.add(TipoAcao.LISTAR_TODOS);
		acoesGet.add(TipoAcao.BUSCA_POR_ID);
	}
	
	private TipoAcao recuperarTipoAcao(HttpServletRequest req) {
		String strAcao = req.getParameter("acao");
		if (StringUtils.trimToEmpty(strAcao).isEmpty()) {
			return TipoAcao.LISTAR_TODOS;
		}
		
		TipoAcao tipoAcao;
		try {
			tipoAcao = TipoAcao.valueOf(strAcao.toUpperCase());			
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return TipoAcao.INVALIDA;
		}
		
		return tipoAcao;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		TipoAcao tipoAcao =  recuperarTipoAcao(req);
		
		if (TipoAcao.LISTAR_TODOS.equals(tipoAcao)) {
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
		
		if (TipoAcao.BUSCA_POR_ID.equals(tipoAcao)) {
			
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
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TipoAcao tipoAcao =  recuperarTipoAcao(req);

		if (TipoAcao.SALVAR.equals(tipoAcao)) {
			
			// TODO Criar método para unificar a leitura da resposta
			BufferedReader buffer = req.getReader();
			
			StringBuilder content = new StringBuilder();
			buffer.lines().forEach(line -> content.append(line.trim()));
			
			ObjectMapper mapper = new ObjectMapper();
			PessoaModel pessoa = mapper.readValue(content.toString(), PessoaModel.class);
			
			PessoaDao dao = new PessoaDao();
			try {
				dao.salvar(pessoa);
			} catch (BancoDadosException ex) {
				LOG.error(ex.getMessage(), ex);
				erroRequisicao(resp, CODIGO_INTERNAL_SERVER_ERROR, ex.getMessage());
				return;
			}
			
			mensagemJson("Cadastro efetuado com sucesso");
			return;
		}
		
		if (TipoAcao.EXCLUSAO.equals(tipoAcao)) {
			// TODO Implementar método de exclusão
		}
		
	}
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		TipoAcao tipoAcao = recuperarTipoAcao(req);
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
