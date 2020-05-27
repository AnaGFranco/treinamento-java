package com.avanade.framework.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.framework.api.data.MensagemData;
import com.avanade.framework.api.data.MensagemData.TipoMensagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractServletAPI extends HttpServlet {

	private static final long serialVersionUID = 6781513970615874829L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractServletAPI.class);
	
	protected static final String METODO_POST = "POST";
	protected static final String METODO_GET = "GET";
	
	protected static final int CODIGO_BAD_REQUEST = 400;
	protected static final int CODIGO_UNAUTHORIZED = 401;
	
	protected static final int CODIGO_INTERNAL_SERVER_ERROR = 500;
	
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

}
