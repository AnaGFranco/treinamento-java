package com.avanade.framework.api.exemplo;

import com.avanade.framework.api.data.MensagemData;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExemploJson {
	
	public static void main(String[] args) throws Exception {

		MensagemData mensagem = new MensagemData();
		mensagem.setMensagem("Teste Mensagem");
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(mensagem);
		System.out.println(json);

		System.out.println("====================");
		
		String jsonFormatado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mensagem);
		System.out.println(jsonFormatado);
	}

}
