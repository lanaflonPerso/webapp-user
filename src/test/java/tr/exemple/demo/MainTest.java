package tr.exemple.demo;

import static org.junit.Assert.*;

import tr.exemple.demo.Main;
import org.junit.Test;

public class MainTest {

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void test() {
		assertTrue( true );
	}

	/**
	 * Un autre test rigoureux :-)
	 */
	@Test
	public void testMain() {
		assertTrue( true );
	}
	
	@Test
	public void testAdditionner() {
//		PropertyConfigurator.configure("log4j.properties");
		Main calcul = new Main();
		int a = 1;
		int b = 2;
		int res = calcul.additionner(a, b);
//		log.trace("@Test additionner(): " + a + " + " + b + " = " + res);
		assertEquals(res, 3);
	}
}
