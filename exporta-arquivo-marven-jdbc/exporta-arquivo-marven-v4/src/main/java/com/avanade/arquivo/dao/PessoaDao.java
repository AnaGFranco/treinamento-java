package com.avanade.arquivo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.avanade.arquivo.model.PessoaModel;
import com.avanade.arquivo.exception.BancoDadosException;

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
}