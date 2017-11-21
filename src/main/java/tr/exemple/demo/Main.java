package tr.exemple.demo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
	
	final static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		log.trace("Hello World");
	}
	
	/**
	 * Addition de deux valeurs entières citées en paramètre
	 * 
	 * @param a int première valeur
	 * @param b int seconde valeur
	 * @return int addition de a+b
	 */
	public int additionner(int a, int b) {
		
		log.trace("Addition : " + a + " + " + b);
		return a + b;
	}

}
