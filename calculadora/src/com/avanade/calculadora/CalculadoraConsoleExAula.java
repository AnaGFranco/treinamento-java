package com.avanade.calculadora;

import java.util.Scanner;

/**
 * Efetuar calculos solicitando valores via console
 * 
 * @author Ana Gabriela
 */

public class CalculadoraConsoleExAula {

	private Scanner scanner; 
	private int operacao;

	public static void main(String[] args) {

		// Iniciando classe da calculadora
		CalculadoraConsoleExAula calc = new CalculadoraConsoleExAula();

		// Inicializa��o
		calc.iniciarCalculadora();

	}

	/**
	 *  Inicializa calculadora
	 */
	private void iniciarCalculadora() {
		scanner = new Scanner(System.in);

		try {
			exibeOperacoesDisponiveis();
			solicitarOperacao();
		} finally {
			scanner.close();
		}
	}

	/**
	 * Verifiica se a string informada � um n�mero v�lido
	 * 
	 * @param strNumero String com o n�mero a ser validados
	 * @return Se � um n�mero v�lido <code>true</code>, sen�o <code>false</code>
	 */
	private boolean validarNumero(String strNumero) {
		try {
			Integer.valueOf(strNumero);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}

	}

	/**
	 * Exibe instru��es para execu��o da aplica��o
	 */

	public static void exibeOperacoesDisponiveis() {
		System.out.println("--------------------------");
		System.out.println("CALCULADORA");
		System.out.println("--------------------------\n");
		System.out.println("Operacoes:");
		System.out.println("1. Adicao");
		System.out.println("2. Subtra��o");
		System.out.println("3. Multiplicacao");
		System.out.println("4. Divisao\n");
		System.out.println("5. Sair\n");
		System.out.println("Selecione uma operacao:");
	}

	/**
	 * Solicita opera��o desejada
	 */

	private void solicitarOperacao() {
		operacao = 0;

		String strOperacao = scanner.next();

		if (!validarNumero(strOperacao)) {
			System.out.println("\n Escolha uma opera��o valida. ");
			solicitarOperacao();
		} else {
			operacao = Integer.valueOf(strOperacao);
			validarOperacao();
		}
	}

	/**
	 * Valida opera��o escolhida
	 */

	private void validarOperacao() {
		if (operacao < 1 || operacao > 5) {

			System.out.println("Escolha uma opera��o valida, digite novamente: ");
			solicitarOperacao();

		} else if (operacao == 5) {

			System.out.println("\n --- At� logo! ---");
			System.exit(0);

		} else {
			solicitarValores();
		}
	}

	/**
	 * Solicita valores
	 */
	private void solicitarValores() {

		int primeiroNumero = solicitarNumero(1);
		int segundoNumero = solicitarNumero(2);

		if (operacao == 4 && segundoNumero == 0) {
			System.err.println("Segundo valor n�o pode ser zero para uma divis�o");
			solicitarValores();
		}
		
		calcular(primeiroNumero,segundoNumero);
		System.out.println("\nCalculo realizado!\n");
		
		
		int op = 0;
		while(op != 1 || op != 2) {
				System.out.println("Aperte 1 para sair, ou 2 para realizar um novo calculo: ");
			 	 op = scanner.nextInt();
			if (op == 1) {
				System.out.println("\n--- At� logo! ---");
				System.exit(0);
				
			}else if (op == 2){
				exibeOperacoesDisponiveis();
				solicitarOperacao();
				return;
			}
		}
	}


	/**
	 * Solicita numeros via console
	 */
	private int solicitarNumero(int ordem) {
		String identificacao = (ordem == 1) ? "primeiro" : "segundo";
		System.out.println("Informe o " + identificacao + " numero: ");
		String strNum = scanner.next();

		if (!validarNumero(strNum)) {

			System.out.println("\n Numero invalido, digite novamente: ");
			return solicitarNumero(ordem);

		} else {
			int num = Integer.valueOf(strNum);
			return num;
		}
	}
	/**
	 * Realiza calculo atraves dos parametros enviado pelo usuario
	 */
	private void calcular(int primeiroNumero,int segundoNumero) {
		
		if (operacao == 1) {
			adicao(primeiroNumero, segundoNumero);
		}
		// Atividade 01 - Implementar as opera��es: subtra��o, divis�o e multiplica��o
		if (operacao == 2) {
			subtracao(primeiroNumero, segundoNumero);
		}
		if (operacao == 3) {
			multiplicacao(primeiroNumero, segundoNumero);
		}
		if (operacao == 4) {
			divisao(primeiroNumero,segundoNumero);
		}	
		

	}
	
	/**
	 * Efetua a soma dos numeros informados
	 */
	private void adicao(int primeiroNumero, int segundoNumero){
		int vtotal = primeiroNumero + segundoNumero;
		System.out.println("\nSelecionado: Adicao\n");
		System.out.println("Valor 1: "+ primeiroNumero);
		System.out.println("Valor 2: "+ segundoNumero);
		System.out.println("\nO resultado �:\n");
		System.out.println(primeiroNumero + " + " + segundoNumero + " = " + vtotal);
		
	}
	
	
	// Atividade 01 - Implementar as opera��es: subtra��o, divis�o e multiplica��o
	
	/**
	 * Efetua a subtracao dos numeros informados
	 */
	private void subtracao(int primeiroNumero, int segundoNumero) {
		int vtotal = primeiroNumero - segundoNumero;
		System.out.println("\nSelecionado: Subtracao\n");
		System.out.println("Valor 1: "+ primeiroNumero);
		System.out.println("Valor 2: "+ segundoNumero);
		System.out.println("\nO resultado �:\n");
		System.out.println(primeiroNumero + " - " + segundoNumero + " = " + vtotal);
		
	}
	
	/**
	 * Efetua a multiplicacao dos numeros informados
	 */
	private void multiplicacao(int primeiroNumero, int segundoNumero) {
		int vtotal = primeiroNumero * segundoNumero;
		System.out.println("\nSelecionado: Multipicacao\n");
		System.out.println("Valor 1: "+ primeiroNumero);
		System.out.println("Valor 2: "+ segundoNumero);
		System.out.println("\nO resultado �:\n");
		System.out.println(primeiroNumero + " * " + segundoNumero + " = " + vtotal);
		
	}
	/**
	 * Efetua a divisao dos numeros informados
	 */
	private void divisao(int primeiroNumero, int segundoNumero) {
		int vtotal = primeiroNumero / segundoNumero;
		System.out.println("\nSelecionado: Divisao\n");
		System.out.println("Valor 1: "+ primeiroNumero);
		System.out.println("Valor 2: "+ segundoNumero);
		System.out.println("\nO resultado �:\n");
		System.out.println(primeiroNumero + " / " + segundoNumero + " = " + vtotal);
	}
}