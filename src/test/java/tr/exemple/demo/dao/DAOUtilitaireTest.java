/**
 * 
 */
package tr.exemple.demo.dao;

import static org.junit.Assert.assertNotNull;
import static tr.exemple.demo.dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Thierry Raimond
 *
 */
public class DAOUtilitaireTest {

    final static Logger log = Logger.getLogger(DAOUtilitaireTest.class);

    final static String TEST_BDD_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    final static String TEST_BDD_CONNECTION_URL = "jdbc:mysql://localhost:3307/bdd_sdzee_test";
    final static String TEST_BDD_USERNAME = "test";
    final static String TEST_BDD_PASSWORD = "test";

    private static int nbTest;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        /* Initialisation du nombre de test */
        nbTest = 0;

        /* Connexion à la base de données */
        log.trace("Tentative de connexion à la base de données.");
        try {
            Class.forName(TEST_BDD_DRIVER_CLASS);
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            log.info("Connexion à la base de données réussie!");

            /* Supprime la table si elle existe déjà */
            log.trace("Tentative de suppréssion de la table bdd_sdzee_test.utilisateur si elle existe déjà.");
            preparedStatement = connexion.prepareStatement("DROP TABLE IF EXISTS utilisateur;");
            preparedStatement.executeUpdate();
            log.info("Suppression de la table bdd_sdzee.utilisateur.");

            /* Creation de la table bdd_sdzee.utilisateur */
            log.trace("Tentative de création de la table bdd_sdzee_test.utilisateur.");
            preparedStatement = connexion.prepareStatement("CREATE TABLE  bdd_sdzee_test.utilisateur ("
                    + "id INT( 11 ) NOT NULL AUTO_INCREMENT ," + "email VARCHAR( 60 ) NOT NULL ,"
                    + "mot_de_passe VARCHAR( 32 ) NOT NULL ," + "nom VARCHAR( 20 ) NOT NULL ,"
                    + "date_inscription DATETIME NOT NULL ," + "PRIMARY KEY ( id )," + "UNIQUE ( email ),"
                    + "UNIQUE ( id )" + ") ENGINE = INNODB DEFAULT CHARSET=utf8;");
            preparedStatement.executeUpdate();
            log.info("Création de la table bdd_sdzee_test.utilisateur.");
        } catch (SQLException e) {
            /* Si l'exception levé affiche que la table 'utilisateur' existe déjà */
            if (e.getMessage().contentEquals("Table 'utilisateur' already exists")) {

            }
            throw new DAOException(e);
        }

        /* Création de la base de données bdd_sdzee_test */
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        log.info("Fin des tests de la classe DAOUtilitaire.java - il y a eu en tout " + nbTest + " tests effectués.");
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        nbTest++;
        log.info("Début du test n° " + nbTest);

        /* Connexion à la base de données */
        try {
            Class.forName(TEST_BDD_DRIVER_CLASS);
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);

            /* Ajout d'un jeu de données */
            log.trace("Tentative d'insertion d'un jeu de données dans la table bdd_sdzee_test.utilisateur.");
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO utilisateur VALUES (1,'coyote@mail.acme',MD5('bipbip'),'Coyote',NOW()),"
                            + "(2,'jadorejquery@unefois.be',MD5('avecdesfrites'),'Thunderseb',NOW());");
            preparedStatement.executeUpdate();
            log.info("Insertion d'un jeu de données dans la table bdd_sdzee_test.utilisateur réussie!");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        /* Connexion à la base de données */
        try {
            Class.forName(TEST_BDD_DRIVER_CLASS);
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);

            /* Suppression des données */
            log.trace("Tentative de suppression des données de la table bdd_sdzee_test.utilisateur.");
            preparedStatement = connexion.prepareStatement("DELETE FROM utilisateur;");
            preparedStatement.executeUpdate();
            log.info("Suppression des données de la table bdd_sdzee_test.utilisateur réussie!");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        log.info("Fin du test n° " + nbTest);
    }

    /**
     * Test method for
     * {@link tr.exemple.demo.dao.DAOUtilitaire#initialisationRequetePreparee(java.sql.Connection, java.lang.String, boolean, java.lang.Object[])}.
     */
    @Test
    public void testInitialisationRequetePreparee() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";
        String email = "coyote@mail.acme";

        try {
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            try {
                preparedStatement = DAOUtilitaire.initialisationRequetePreparee(connexion, SQL_SELECT_PAR_EMAIL, false,
                        email);
                log.trace(preparedStatement);
                assertNotNull(preparedStatement);
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                fermeturesSilencieuses(resultSet, preparedStatement, connexion);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Test method for {@link tr.exemple.demo.dao.DAOUtilitaire#fermetureSilencieuse(java.sql.ResultSet)}.
     */
    @Test
    public void testFermetureSilencieuseResultSet() {
        // TODO fail("Not yet implemented");
    }

    /**
     * Test method for {@link tr.exemple.demo.dao.DAOUtilitaire#fermetureSilencieuse(java.sql.Statement)}.
     */
    @Test
    public void testFermetureSilencieuseStatement() {
        // TODO fail("Not yet implemented");
    }

    /**
     * Test method for {@link tr.exemple.demo.dao.DAOUtilitaire#fermetureSilencieuse(java.sql.Connection)}.
     */
    @Test
    public void testFermetureSilencieuseConnection() {
        // TODO fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link tr.exemple.demo.dao.DAOUtilitaire#fermeturesSilencieuses(java.sql.Statement, java.sql.Connection)}.
     */
    @Test
    public void testFermeturesSilencieusesStatementConnection() {
        // TODO fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link tr.exemple.demo.dao.DAOUtilitaire#fermeturesSilencieuses(java.sql.ResultSet, java.sql.Statement, java.sql.Connection)}.
     */
    @Test
    public void testFermeturesSilencieusesResultSetStatementConnection() {
        // TODO fail("Not yet implemented");
    }

}
