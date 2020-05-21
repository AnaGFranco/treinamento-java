package com.avanade.arquivo.exception;


public class ConexaoNaoIniciadaException  extends RuntimeException{

	private static final long serialVersionUID = 9009448931357843154L;

	public ConexaoNaoIniciadaException() {
		super();
	}
	
	public ConexaoNaoIniciadaException(String mensagem) {
		super(mensagem);
	}
}
