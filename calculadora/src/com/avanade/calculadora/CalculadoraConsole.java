package com.avanade.calculadora;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

//import javax.swing.JOptionPane;


/**
 * Efetuar calculos matemáticos solicitando valores para o usuário
 * 
 * @author Ana Gabriela
 */

public class CalculadoraConsole {

	private static final List<String> LISTA_OPERACOES = new ArrayList<String>();
	
	private static final String OPER_ADICAO = "1";
	private static final String OPER_SUBTRACAO = "2";
	private static final String OPER_MULTIPLICACAO = "3";
	private static final String OPER_DIVISAO = "4";

	
	static {
		// Inicializar lista de operações
		LISTA_OPERACOES.add(OPER_ADICAO);
		LISTA_OPERACOES.add(OPER_SUBTRACAO);
		LISTA_OPERACOES.add(OPER_MULTIPLICACAO);
		LISTA_OPERACOES.add(OPER_DIVISAO);
	}

	public static void main(String[] args) {



		// Iniciando classe da calculadora
		CalculadoraConsole calc = new CalculadoraConsole();
		
		// Inicialização
		calc.iniciaCalculadora();

	}

	/**
	 * Imprime instruções para execução da aplicação
	 */
	
	public static void imprimirOperacoesDisponiveis() {
		System.out.println("--------------------------");
		System.out.println("CALCULADORA");
	    System.out.println("--------------------------\n");
	    System.out.println("Operacoes:");
	    System.out.println("1. Adicao");
	    System.out.println("2. Subtração");
	    System.out.println("3. Multiplicacao");
	    System.out.println("4. Divisao\n");		
	}
	
	private void iniciaCalculadora() {
		imprimirOperacoesDisponiveis();
		
		Scanner scanner = new Scanner(System.in);

		    System.out.println("Selecione uma operacao:");
		    String strOperacao = scanner.nextLine();
		   
		    System.out.println("Entre com o primeiro numéro inteiro: ");
		    String strNum1 = scanner.nextLine();
		    
		    System.out.println("Entre com o segundo numéro inteiro:");
		    String strNum2 = scanner.nextLine();
		    
		    scanner.close();
		    
		if (!LISTA_OPERACOES.contains(strOperacao)) {
			System.err.println("Opção indisponivel [valor = " + strOperacao + "]");
			return;
		}
		
		if (!validarNumero(strNum1)) {
			System.err.println("Primeiro valor não é um número válido [valor = " + strNum1 + "]");
			return;
		}
		
		if (!validarNumero(strNum2)) {
			System.err.println("Segundo valor não é um número válido [valor = " + strNum2 + "]");
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
	 * @throws IOException 
	 */
	

	private void adicao(int num1, int num2){
		int vtotal = num1 + num2;
		System.out.println("\nSelecionado: Adicao\n");
		System.out.println("Valor 1: "+ num1);
		System.out.println("Valor 2: "+ num2);
		System.out.println("\nO resultado é:\n");
		System.out.println(num1 + " + " + num2 + " = " + vtotal);
		
	}
	
	
	// Atividade 01 - Implementar as operações: subtração, divisão e multiplicação
	
	/**
	 * Efetua a subtracao dos numeros informados
	 * @param num1 Primeiro valor
	 * @param num2 Segundo valor
	 * @throws IOException 
	 */
	
	private void subtracao(int num1, int num2) {
		int vtotal = num1 - num2;
		System.out.println("\nSelecionado: Subtracao\n");
		System.out.println("Valor 1: "+ num1);
		System.out.println("Valor 2: "+ num2);
		System.out.println("\nO resultado é:\n");
		System.out.println(num1 + " - " + num2 + " = " + vtotal);
		
	}
	
	/**
	 * Efetua a multiplicacao dos numeros informados
	 * @param num1 Primeiro valor
	 * @param num2 Segundo valor
	 * @throws IOException 
	 */
	private void multiplicacao(int num1, int num2) {
		int vtotal = num1 * num2;
		System.out.println("\nSelecionado: Multipicacao\n");
		System.out.println("Valor 1: "+ num1);
		System.out.println("Valor 2: "+ num2);
		System.out.println("\nO resultado é:\n");
		System.out.println(num1 + " * " + num2 + " = " + vtotal);
		
	}
	/**
	 * Efetua a divisao dos numeros informados
	 * @param num1 Primeiro valor
	 * @param num2 Segundo valor
	 * @throws IOException 
	 */
	private void divisao(int num1, int num2) {
		int vtotal = num1 / num2;
		System.out.println("\nSelecionado: Divisao\n");
		System.out.println("Valor 1: "+ num1);
		System.out.println("Valor 2: "+ num2);
		System.out.println("\nO resultado é:\n");
		System.out.println(num1 + " / " + num2 + " = " + vtotal);
	}
	
}