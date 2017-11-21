package tr.exemple.demo.dao;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.log4j.Logger;

import tr.exemple.demo.dao.DAOFactory;
import org.junit.Test;

public class DAOFactoryTest {

	final static Logger log = Logger.getLogger(DAOFactoryTest.class);
	
	@Test
	public void testDAOFactory() {
		//TODO fail("Not yet implemented");
	}

	@Test
	public void testGetInstance() {
		//TODO fail("Not yet implemented");
	}

	@Test
	public void testGetConnection() {
		//TODO fail("Not yet implemented");
	}

	@Test
	public void testGetPropertiesFile() {
		InputStream fichierProperties1 = DAOFactory.getPropertiesFile("chemin bidon");
		log.trace("@TEST getPropertiesFile() : " + fichierProperties1);
		assertNull(fichierProperties1);
	}

	
}
