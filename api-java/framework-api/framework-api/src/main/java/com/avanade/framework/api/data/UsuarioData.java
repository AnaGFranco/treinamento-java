package com.avanade.framework.api.data;

import java.io.Serializable;
import java.util.Date;

public class UsuarioData implements Serializable {

	private static final long serialVersionUID = -2265876280376117083L;

	private String login;
	private String chaveAcesso;
	private Date dataAcesso;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getChaveAcesso() {
		return chaveAcesso;
	}

	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

}
