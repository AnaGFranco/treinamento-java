package com.avanade.framework.api.crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.framework.api.AbstractServletAPI;

public abstract class AbstractCrud extends AbstractServletAPI {

	protected static final long serialVersionUID = 388881663919606915L;
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractCrud.class);

	protected static List<TipoAcao> acoesGet;
	protected enum TipoAcao {LISTAR_TODOS, BUSCA_POR_ID, SALVAR, EXCLUSAO, INVALIDA}
	protected TipoAcao tipoAcao;


	static {
		acoesGet = new ArrayList<AbstractCrud.TipoAcao>();
		acoesGet.add(TipoAcao.LISTAR_TODOS);
		acoesGet.add(TipoAcao.BUSCA_POR_ID);
	}
	
	
	
	protected TipoAcao recuperarTipoAcao(HttpServletRequest req) {
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
	
	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		return;

	}

}
