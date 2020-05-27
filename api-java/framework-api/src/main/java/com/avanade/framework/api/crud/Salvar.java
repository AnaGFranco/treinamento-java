package com.avanade.framework.api.crud;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avanade.db.dao.PessoaDao;
import com.avanade.db.exception.BancoDadosException;
import com.avanade.db.model.PessoaModel;
import com.avanade.framework.api.AbstractServletAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Salvar extends AbstractServletAPI {
	
	private static final Logger LOG = LoggerFactory.getLogger(Salvar.class);


	private static final long serialVersionUID = 1492231680412620676L;

	public void executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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

		mensagemJson("Cadastrado com sucesso!!");
		return;

	}

}
