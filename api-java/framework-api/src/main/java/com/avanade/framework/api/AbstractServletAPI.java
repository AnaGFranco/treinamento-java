package com.avanade.framework.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.framework.api.data.MensagemData;
import com.avanade.framework.api.data.MensagemData.TipoMensagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractServletAPI extends HttpServlet {

	private static final long serialVersionUID = 6781513970615874829L;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractServletAPI.class);

	//METODOS
	protected static final String METODO_POST = "POST";
	protected static final String METODO_GET = "GET";

	//STATUS_CODE
	protected static final int CODIGO_BAD_REQUEST = 400;
	protected static final int CODIGO_UNAUTHORIZED = 401;
	protected static final int CODIGO_INTERNAL_SERVER_ERROR = 500;
	
	protected TipoAcao tipoAcao;

	
	// LISTA DE TIPO AÇÃO
	protected enum TipoAcao {
		LISTAR_TODOS, BUSCA_POR_ID, SALVAR, EXCLUSAO, INVALIDA
	}
	
	/**
	 * trata ação socilitada
	 * 
	 * @param req request
	 * @return tipo de ação recuperado
	 */
	protected TipoAcao recuperarTipoAcao(HttpServletRequest req) {
		String strAcao = req.getParameter("acao");
		if (StringUtils.trimToEmpty(strAcao).isEmpty()) {
			return TipoAcao.LISTAR_TODOS;
		}
		
		try {
			tipoAcao = TipoAcao.valueOf(strAcao.toUpperCase());			
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return TipoAcao.INVALIDA;
		}
		
		return tipoAcao;
	}

	/**
	 * Exibir dados do request
	 * @param req
	 */
	protected void exibirDadosRequest(HttpServletRequest req) {

		LOG.debug("========= service ============");
		LOG.debug("Método: " + req.getMethod());

		LOG.debug("Cabeçalhos Request");
		LOG.debug("===========================");

		Iterator<String> headers = req.getHeaderNames().asIterator();

		headers.forEachRemaining(headerName -> {
			String headerValue = req.getHeader(headerName);
			LOG.debug(headerName + " = " + headerValue);
		});

		LOG.debug("");

		LOG.debug("Parametros Request");
		LOG.debug("===========================");

		Iterator<String> params = req.getParameterNames().asIterator();
		params.forEachRemaining(paramName -> {
			String paramValue = req.getParameter(paramName);
			LOG.debug(paramName + " = " + paramValue);
		});
	}

	protected boolean isPost(HttpServletRequest req) {
		return isPost(req.getMethod());
	}

	protected boolean isPost(String metodo) {
		return METODO_POST.equals(metodo);
	}

	protected boolean isGet(HttpServletRequest req) {
		return isGet(req.getMethod());
	}

	protected boolean isGet(String metodo) {
		return METODO_GET.equals(metodo);
	}
	
	/**
	 * Converter objeto para json
	 * @param objeto
	 * @return
	 */

	protected String objetoEmJson(Object objeto) {
		ObjectMapper mapper = new ObjectMapper();
		String json;

		try {
			json = mapper.writeValueAsString(objeto);
		} catch (JsonProcessingException ex) {
			LOG.error("Falha ao converter objeto para json", ex);
			json = "{\"erro\" : \"" + ex.getMessage() + "\"}";
		}

		return json;
	}

	protected String mensagemJson(String mensagem) {
		return mensagemJson(mensagem, TipoMensagem.INFO);
	}

	protected String mensagemJson(String mensagem, TipoMensagem tipo) {

		MensagemData mensagemData = new MensagemData();
		mensagemData.setTipo(tipo);
		mensagemData.setMensagem(mensagem);

		return objetoEmJson(mensagemData);
	}

	protected void retornoJson(HttpServletResponse resp, String json) throws IOException {
		resp.setContentType("application/json");
		PrintWriter pw = resp.getWriter();
		pw.write(json);
		pw.close();
	}

	protected void erroRequisicao(HttpServletResponse resp, int codigo, String mensagem) throws IOException {
		resp.setStatus(codigo);
		retornoJson(resp, mensagemJson(mensagem, TipoMensagem.ERRO));
	}

	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return;
	}

	public Integer validaCodigo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String strCodigo = req.getParameter("codigo");
		if (StringUtils.trimToEmpty(strCodigo).isEmpty()) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Codigo para consulta nao informado");
			return null;
		}

		if (!StringUtils.isNumeric(strCodigo)) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Codigo informado deve ser um numero valido");
			return null;
		}

		Integer codigo = Integer.valueOf(strCodigo);
		return codigo;

	}

}
