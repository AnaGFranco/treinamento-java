package com.avanade.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.UsuarioModel;

public class UsuarioDao {
	
	public UsuarioModel consultarUsuarioPorLogin(String login) throws BancoDadosException {
		
		try {
			Connection conn = GerenciaConexao.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("select codigo ");
			sql.append("     , login ");
			sql.append("     , senha ");
			sql.append(" from Usuario ");
			sql.append("where login = '" + login + "'");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql.toString());
			
			if (!rs.next()) {
				return null;
			}
			
			int col = 1;
			Integer rsCodigo = rs.getInt(col++);
			String rsLogin = rs.getString(col++);
			String rsSenha = rs.getString(col++);
			
			UsuarioModel usuario = new UsuarioModel();
			usuario.setCodigo(rsCodigo);
			usuario.setLogin(rsLogin);
			usuario.setSenha(rsSenha);
			
			return usuario;
			
		} catch (SQLException ex) {
			throw new BancoDadosException("Erro ao consultar tabela "
					+ "Pessoa [login = " + login + "]", ex);
		}
		
	}

	
	
}
