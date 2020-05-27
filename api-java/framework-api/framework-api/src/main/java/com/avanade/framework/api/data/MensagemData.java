package com.avanade.framework.api.data;

import java.io.Serializable;
import java.util.Date;

public class MensagemData implements Serializable {

	private static final long serialVersionUID = -5240293651550525630L;

	public enum TipoMensagem {
		ERRO, INFO
	}

	private TipoMensagem tipo;
	private String mensagem;
	private Date dataHora;
	

	public TipoMensagem getTipo() {
		if (tipo == null) {
			tipo = TipoMensagem.INFO;
		}
		return tipo;
	}

	public void setTipo(TipoMensagem tipo) {
		this.tipo = tipo;
	}

	public String getMensagem() {
		if (mensagem == null) {
			mensagem = "";
		}
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.dataHora = new Date();
		this.mensagem = mensagem;
	}
	
	public Date getDataHora() {
		if (dataHora == null) {
			dataHora = new Date(1);
		}
		return dataHora;
	}
	
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

}
