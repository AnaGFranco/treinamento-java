package com.avanade.db;

import java.io.IOException;
import java.sql.SQLException;

import com.avanade.db.config.ConfiguracaoDbGlobal;
import com.avanade.db.dao.GerenciaConexao;

public class CarregarDb {


	public static void iniciar() throws IOException, ClassNotFoundException, SQLException {

		 	// carregar config
			ConfiguracaoDbGlobal.carregarConfiguracao();
		

	
			// Conectando com o banco de dados
			GerenciaConexao.openConnection();
	
	
	}


	
}
