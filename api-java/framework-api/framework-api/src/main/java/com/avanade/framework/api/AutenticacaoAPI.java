package com.avanade.framework.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.UsuarioDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.UsuarioModel;
import com.avanade.framework.api.data.TokenData;
import com.avanade.framework.api.data.UsuarioData;

public class AutenticacaoAPI extends AbstractServletAPI {

	private static final long serialVersionUID = 2325566665602223672L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AutenticacaoAPI.class);
	
	private static final String SESSION_USUARIO = "usuario";

	private void tratarPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String login = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		if (StringUtils.trimToEmpty(login).isEmpty()) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Login nao informado");
			return;
		}
		
		if (StringUtils.trimToEmpty(senha).isEmpty()) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Senha nao informada");
			return;
		}
		
		UsuarioDao dao = new UsuarioDao();
		UsuarioModel usuarioModel;
		try {
			usuarioModel = dao.consultarUsuarioPorLogin(login);
		} catch (BancoDadosException ex) {
			LOG.error(ex.getMessage(), ex);
			erroRequisicao(resp, CODIGO_INTERNAL_SERVER_ERROR, ex.getMessage());
			return;
		}
		
		if (!usuarioModel.getLogin().equals(login)) {
			erroRequisicao(resp, CODIGO_UNAUTHORIZED, "Usuario informado nao e valido");
			return;
		}
		
		if (!usuarioModel.getSenha().equals(senha)) {
			erroRequisicao(resp, CODIGO_UNAUTHORIZED, "Senha invalida");
			return;
		}
	
		UsuarioData usuario = new UsuarioData();
		usuario.setLogin(login);
		usuario.setDataAcesso(new Date());
		
		UUID uuid = UUID.randomUUID();
		usuario.setChaveAcesso(uuid.toString());
		
		HttpSession session = req.getSession();
		session.setAttribute(SESSION_USUARIO, usuario);
		
		TokenData token = new TokenData();
		token.setMensagem("Usuario autenticado com sucesso");
		token.setToken(usuario.getChaveAcesso());
		
		resp.setContentType("application/json");
		
		PrintWriter pw = resp.getWriter();
		pw.write(objetoEmJson(token));
		pw.close();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		session.removeAttribute(SESSION_USUARIO);
		
		if (!isPost(req.getMethod())) {
			erroRequisicao(resp, CODIGO_BAD_REQUEST, "Apenas metodo POST permitido para requisicao");
			return;
		}
		
		tratarPost(req, resp);
		
	}


	
	
	
}
