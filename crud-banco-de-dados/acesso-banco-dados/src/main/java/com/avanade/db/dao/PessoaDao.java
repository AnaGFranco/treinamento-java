package com.avanade.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;

public class PessoaDao {
	
	public List<PessoaModel> listarPessoas() throws BancoDadosException {
	    try {
	        Connection conn = GerenciaConexao.getConnection();
	        
	        StringBuilder sql = new StringBuilder();
	        sql.append("select codigo ");
	        sql.append(", nome ");
	        sql.append(", endereco ");
	        sql.append("from Pessoa ");
	        Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery(sql.toString());
	        
	        List<PessoaModel> lista = new  ArrayList<PessoaModel>();
	        
	        while (rs.next()) {
	            int col = 1;
	            Integer rsCodigo = rs.getInt(col++);
	            String rsNome = rs.getString(col++);
	            String rsEndereco = rs.getString(col++);
	            
	            PessoaModel pessoa = new PessoaModel();
	            pessoa.setCodigo(rsCodigo);
	            pessoa.setNome(rsNome);
	            pessoa.setEndereco(rsEndereco);
	            
	            lista.add(pessoa);
	        }
	        
	        return lista;
	        
	    } catch (SQLException ex) {
	        throw new BancoDadosException("Erro ao listar todos os registros "
	                + "da tabela Pessoa", ex);
	    }
	}
	
	public void salvar(PessoaModel pessoa) throws BancoDadosException {
		
		PessoaModel tmpPessoa = consultaPorCodigo(pessoa.getCodigo());
		
	    try {
			Connection conn = GerenciaConexao.getConnection();
							
			StringBuilder sql = new StringBuilder();
			
			if(tmpPessoa == null) {
				sql.append("insert into Pessoa (nome,endereco,codigo)");
				sql.append("			values (?, ?, ?)");
			}else {
				sql.append("update Pessoa");
				sql.append("   set nome = ? ");
				sql.append("     , endereco = ? ");
				sql.append(" where codigo = ? ");
			}
		
			PreparedStatement st = conn.prepareStatement(sql.toString());
			int idx = 1;
			st.setString(idx++, pessoa.getNome());
			st.setString(idx++, pessoa.getEndereco());
			st.setInt(idx++, pessoa.getCodigo());
			
			st.executeUpdate();
		
		} catch (SQLException ex) {
			throw new BancoDadosException("Erro ao salvar tabela Pessoa", ex);
		}
		
	}
	
	public PessoaModel consultaPorCodigo(Integer codigo) throws BancoDadosException {
		
		try {
			Connection conn = GerenciaConexao.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("select codigo ");
			sql.append("     , nome ");
			sql.append("     , endereco ");
			sql.append(" from Pessoa ");
			sql.append("where codigo = " + codigo);
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql.toString());
			
			if (!rs.next()) {
				return null;
			}
			
			int col = 1;
			Integer rsCodigo = rs.getInt(col++);
			String rsNome = rs.getString(col++);
			String rsEndereco = rs.getString(col++);
			
			PessoaModel pessoa = new PessoaModel();
			pessoa.setCodigo(rsCodigo);
			pessoa.setNome(rsNome);
			pessoa.setEndereco(rsEndereco);
			
			return pessoa;
			
		} catch (SQLException ex) {
			throw new BancoDadosException("Erro ao consultar tabela "
					+ "Pessoa [codigo = " + codigo + "]", ex);
		}
		
	}
}