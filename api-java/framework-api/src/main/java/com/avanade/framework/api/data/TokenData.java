package com.avanade.framework.api.data;

public class TokenData extends MensagemData {

	private static final long serialVersionUID = -5178796651291893846L;

	private String token;

	public String getToken() {
		if (token == null) {
			token = "";
		}
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
