package com.avanade.calculadora;

import java.util.ArrayList;
import java.util.List;

/**
 * Efetuar calculos matemáticos com base em valores recebido em parâmetros na
 * inicialização da aplicação
 * 
 */
public class CalculadoraArgumentos {

	private static final int NUMERO_ARGUMENTOS_CALCULO = 3;
	private static final List<String> LISTA_OPERACOES = new ArrayList<String>();
	
	private static final String OPER_ADICAO = "a";
	private static final String OPER_SUBTRACAO = "s";
	private static final String OPER_MULTIPLICACAO = "m";
	private static final String OPER_DIVISAO = "d";
	
	static {
		// Inicializar lista de operações
		LISTA_OPERACOES.add(OPER_ADICAO);
		LISTA_OPERACOES.add(OPER_SUBTRACAO);
		LISTA_OPERACOES.add(OPER_MULTIPLICACAO);
		LISTA_OPERACOES.add(OPER_DIVISAO);
	}

	public static void main(String[] args) {

		// Validação dos argumentos
		if (args.length != NUMERO_ARGUMENTOS_CALCULO) {
			immprimirInstrucoes();
			return;
		}

		// Iniciando classe da calculadora
		CalculadoraArgumentos calc = new CalculadoraArgumentos();
		
		// Carrega argumentos informados
		String strNum1 = args[0];
		String strNum2 = args[1];
		String strOperacao = args[2];
		
		// Inicialização
		calc.iniciaCalculadora(strNum1, strNum2, strOperacao);
	}

	/**
	 * Imprime instruções para execução da aplicação
	 */
	public static void immprimirInstrucoes() {
		System.out.println("Calculadore com argumentos");
		System.out.println("Usar:");
		System.out.println("java CalculadoraArgumentos [numero1] [numero2] [operacao]");
		imprimirOperacoesDisponiveis();
	}
	
	public static void imprimirOperacoesDisponiveis() {
		System.out.println("Operacoes disponiveis: a = adicao, s = subtracao, m = multipicacao, d = divisao");
	}

	/**
	 * Efetua a inicialização da calculadora
	 * 
	 * @param strNum1     String contendo o primeiro número para cálculo
	 * @param strNum2     String contendo o segundo número para cálculo
	 * @param strOperacao Tipo de operações que devem ser: a = adicao, s =
	 *                    subtracao, m = multiplicacao, d = divisao
	 */
	private void iniciaCalculadora(String strNum1, String strNum2, String strOperacao) {
		
		if (!validarNumero(strNum1)) {
			System.err.println("Primeiro valor não é um número válido [valor = " + strNum1 + "]");
			return;
		}
		
		if (!validarNumero(strNum2)) {
			System.err.println("Segundo valor não é um número válido [valor = " + strNum2 + "]");
			return;
		}
		
		if (!LISTA_OPERACOES.contains(strOperacao)) {
			System.err.println("Terceiro parametro não é uma operação válida [valor = " + strOperacao + "]");
			imprimirOperacoesDisponiveis();
			return;
		}
		
		int num1 = Integer.valueOf(strNum1);
		int num2 = Integer.valueOf(strNum2);

		if (strOperacao.equals(OPER_DIVISAO) && num2 == 0) {
			System.err.println("Segundo valor não pode ser zero para uma divisão");
			return;
		}
		
		if (OPER_ADICAO.equals(strOperacao)) {
			adicao(num1, num2);
		}
		// Atividade 01 - Implementar as operações: subtração, divisão e multiplicação
		if (OPER_SUBTRACAO.equals(strOperacao)) {
			subtracao(num1, num2);
		}
		if (OPER_MULTIPLICACAO.equals(strOperacao)) {
			multiplicacao(num1, num2);
		}
		if (OPER_DIVISAO.equals(strOperacao)) {
			divisao(num1, num2);
		}
	}
	
	/**
	 * Verifiica se a string informada é um número válido
	 * 
	 * @param strNumero String com o número a ser validados
	 * @return Se é um número válido <code>true</code>, senão <code>false</code>
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
	 * Efetua a soma dos numeros informados
	 * @param num1 Primeiro valor
	 * @param num2 Segundo valor
	 */
	private void adicao(int num1, int num2) {
		int vtotal = num1 + num2;
		System.out.println("Adicao");
		System.out.println("=============");
		System.out.println(num1 + " + " + num2 + " = " + vtotal);
		
	}
	
	// Atividade 01 - Implementar as operações: subtração, divisão e multiplicação
	private void subtracao(int num1, int num2) {
		int vtotal = num1 - num2;
		System.out.println("Subtracao");
		System.out.println("=============");
		System.out.println(num1 + " - " + num2 + " = " + vtotal);
		
	}
	
	private void multiplicacao(int num1, int num2) {
		int vtotal = num1 * num2;
		System.out.println("Multipicacao");
		System.out.println("=============");
		System.out.println(num1 + " * " + num2 + " = " + vtotal);
		
	}
	
	private void divisao(int num1, int num2) {
		int vtotal = num1 / num2;
		System.out.println("Divisao");
		System.out.println("=============");
		System.out.println(num1 + " / " + num2 + " = " + vtotal);
		
	}
	
}












































