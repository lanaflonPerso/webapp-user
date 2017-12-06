package tr.exemple.demo.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.jolbox.bonecp.BoneCP;

public class DAOFactoryTest {

    final static Logger log = Logger.getLogger(DAOFactoryTest.class);

    @Test
    public void testDAOFactory() {
        // TODO fail("Not yet implemented");
    }

    @Test
    public void testGetInstance() {
        assertNotNull(DAOFactory.getInstance());
    }

    @Test
    public void testGetConnection() {
        // TODO fail("Not yet implemented");
    }

    @Test
    public void testGetInputStream() {

        InputStream fichierProperties1 = DAOFactory.getInputStream("chemin bidon");
        log.trace("@TEST getPropertiesFile() : " + fichierProperties1);
        assertNull(fichierProperties1);

        // InputStream fichierProperties2 = DAOFactory.getInputStream("/tr/exemple/demo/dao/dao.properties");
        // log.trace("@TEST getPropertiesFile() : " + fichierProperties2);
        // assertNotNull(fichierProperties2);
    }

    // @Test
    // public void testPropertiesLoad() {
    // Properties properties = new Properties();
    //
    // try {
    // FileInputStream input = new FileInputStream("src/main/resources/dao.properties");
    // log.trace("FileInputStream input : " + input);
    // try {
    // properties.load(input);
    // log.trace("properties : " + properties);
    // log.trace("properties.getProperty(\"url\") : " + properties.getProperty("url"));
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }

    @Test
    public void testGetFileInputStream() {
        // FileInputStream fichierProperties1 = DAOFactory.getFileInputStream("dao.properties");
        // log.trace("DAOFactory.load(\"dao.properties\") : " + fichierProperties1);

        // FileInputStream fichierProperties2 = DAOFactory.getFileInputStream("src/main/resources/dao.properties");
        // log.trace("DAOFactory.load(\"src/main/resources/dao.properties\") : " + fichierProperties2);

        FileInputStream fichierProperties3 = DAOFactory.getFileInputStream("src/main/resources/log4j.properties");
        log.trace("DAOFactory.load(\"log4j.properties\") : " + fichierProperties3);
        assertNotNull(fichierProperties3);

    }

    // @Test
    // public void testInputStream() {
    // InputStream input;
    // input = DAOFactory.class.getResourceAsStream("dao.properties");
    // log.trace("DAOFactory.class.getResourceAsStream(\"dao.properties\") : " + input);
    // log.trace("DAOFactory.class.getResource(\"dao.properties\").getFile() : " +
    // DAOFactory.class.getResource("dao.properties").getFile());
    //
    // InputStream input2 = this.getClass().getResourceAsStream("dao.properties");
    // log.trace("this.getClass().getResourceAsStream(\"dao.properties\") : " + input2);
    // log.trace("this.getClass().getResource(\"dao.properties\").getFile() : " +
    // this.getClass().getResource("dao.properties").getFile());
    //
    // InputStream input3 = this.getClass().getResourceAsStream("log4j.properties");
    // log.trace("this.getClass().getResourceAsStream(\"log4j.properties\") : " + input3);
    // log.trace("this.getClass().getResource(\"log4j.properties\").getFile() : " +
    // this.getClass().getResource("log4j.properties").getFile());
    // }

    @Test
    public void testGetPropertiesKeysValues() {
        HashMap<String, String> hmap = new HashMap<String, String>();
        FileInputStream fichierProperties = DAOFactory.getFileInputStream("src/main/resources/dao.properties");
        hmap = DAOFactory.getPropertiesKeysValues(fichierProperties);
        log.trace("HashMap<String, String> hmap : " + hmap);
        assertNotNull(hmap);
    }

    @Test
    public void testConnexionDriverJdbc() {
        // test connexion driver jdbc réussie
        HashMap<String, String> hmap = new HashMap<String, String>();
        FileInputStream fichierProperties = DAOFactory.getFileInputStream("src/main/resources/dao.properties");
        hmap = DAOFactory.getPropertiesKeysValues(fichierProperties);
        DAOFactory.connexionDriverJdbc(hmap);

    }

    @Test(expected = DAOConfigurationException.class)
    public void testConnexionDriverJdbcWithException() {
        // test connexion driver jdbc échoué
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("driver", "bidon");
        DAOFactory.connexionDriverJdbc(hmap);
    }

    @Test
    public void testConnexionBoneCP() {
        // Création d'une configuration de pool réussie
        HashMap<String, String> hmap = new HashMap<String, String>();
        FileInputStream fichierProperties = DAOFactory.getFileInputStream("src/main/resources/dao.properties");
        hmap = DAOFactory.getPropertiesKeysValues(fichierProperties);
        assertNotNull(DAOFactory.connexionBoneCP(hmap));
    }

    @Test(expected = DAOConfigurationException.class)
    public void testConnexionBoneCPWithException() {
        // test d'une configuration de pool en échec
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("ssl", "false");
        hmap.put("url", "bidon");
        hmap.put("nomutilisateur", "bidon");
        hmap.put("motdepasse", "bidon");
        BoneCP connexionPool = DAOFactory.connexionBoneCP(hmap);
    }
}
