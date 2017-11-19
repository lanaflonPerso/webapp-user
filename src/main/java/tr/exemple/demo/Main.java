package tr.exemple.demo;

import org.apache.log4j.PropertyConfigurator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");		
	}
	
	/**
	 * Addition de deux valeurs entières citées en paramètre
	 * 
	 * @param a int première valeur
	 * @param b int seconde valeur
	 * @return int addition de a+b
	 */
	public int additionner(int a, int b) {
//		PropertyConfigurator.configure("log4j.properties");
//		log.trace("Addition : " + a + " + " + b);
		return a + b;
	}

}
