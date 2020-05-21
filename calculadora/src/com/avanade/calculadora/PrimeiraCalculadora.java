package com.avanade.calculadora;

public class PrimeiraCalculadora {

	public static void main(String[] args) {
		
		for(String arg : args) {
			System.out.println(arg);
		}
		
		PrimeiraCalculadora result = new PrimeiraCalculadora();
	
		
		result.calcular(args[0],args[1],args[2]);
		
	}
	
	public void calcular(String n1,String operador, String n2) {
		
	      double a = Double.parseDouble(n1);
	      double b = Double.parseDouble(n2);
	      double resultado = 0.0;
	      

		if (operador.equals("+")) {
			resultado = a + b;
		} else if (operador.equals("-")) {
			resultado = a - b;
	    } else if (operador.equals("/")) {
	    	resultado = a / b;
	    } else if (operador.equals("*")) {
	    	resultado = a * b;
	    }
		
		System.out.println("O resultado é: " + resultado);
		
	 }
}


