/**
 * 
 */
package tr.exemple.demo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static tr.exemple.demo.dao.DAOUtilitaire.fermeturesSilencieuses;
import static tr.exemple.demo.dao.DAOUtilitaire.rollback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tr.exemple.demo.beans.Utilisateur;

/**
 * @author Thierry Raimond
 *
 */
public class UtilisateurDaoTest {

    final static Logger log = Logger.getLogger(UtilisateurDaoTest.class);

    private static DAOFactory daoFactory;

    private static int nbTest;

    private static Connection connexion;
    private static PreparedStatement preparedStatement;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        /* Instanciation du dao de développement */
        daoFactory = DAOFactory.getInstance(false);

        /* Initialisation du nombre de test */
        nbTest = 0;

        try {
            /* Récupération de la connexion à la bdd de développement depuis la Factory */
            connexion = daoFactory.getConnection();

            /* Supprime la table "bdd_sdzee_test.utilisateur" si elle existe déjà */
            log.trace("Tentative de suppréssion de la table bdd_sdzee_test.utilisateur si elle existe déjà.");
            preparedStatement = connexion.prepareStatement("DROP TABLE IF EXISTS utilisateur;");
            preparedStatement.executeUpdate();
            log.info("Suppression de la table bdd_sdzee_test.utilisateur.");

            /* Creation de la table bdd_sdzee_test.utilisateur */
            log.trace("Tentative de création de la table bdd_sdzee_test.utilisateur.");
            preparedStatement = connexion.prepareStatement("CREATE TABLE  bdd_sdzee_test.utilisateur ("
                    + "id INT( 11 ) NOT NULL AUTO_INCREMENT ," + "email VARCHAR( 60 ) NOT NULL ,"
                    + "mot_de_passe VARCHAR( 32 ) NOT NULL ," + "nom VARCHAR( 20 ) NOT NULL ,"
                    + "date_inscription DATETIME NOT NULL ," + "PRIMARY KEY ( id )," + "UNIQUE ( email ),"
                    + "UNIQUE ( id )" + ") ENGINE = INNODB DEFAULT CHARSET=utf8;");
            preparedStatement.executeUpdate();
            log.info("Création de la table bdd_sdzee_test.utilisateur.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {

        log.info("Fin des tests de la classe UtilitaireDao.java - il y a eu en tout " + nbTest + " tests effectués.");

        try {
            /* Récupération de la connexion à la bdd de développement depuis la Factory */
            connexion = daoFactory.getConnection();

            /* Ajout d'un jeu de données */
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO utilisateur VALUES (1,'coyote@mail.acme',MD5('bipbip'),'Coyote',NOW()),"
                            + "(2,'jadorejquery@unefois.be',MD5('avecdesfrites'),'Thunderseb',NOW());");
            preparedStatement.executeUpdate();
            connexion.commit();
        } catch (SQLException e) {
            rollback(connexion);
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        nbTest++;
        log.info("Début du test n° " + nbTest);

        /* Connexion à la base de données */
        try {
            /* Récupération de la connexion à la bdd de développement depuis la Factory */
            connexion = daoFactory.getConnection();

            /* Ajout d'un jeu de données */
            log.trace("Tentative d'insertion d'un jeu de données dans la table bdd_sdzee_test.utilisateur.");
            preparedStatement = connexion.prepareStatement(
                    "INSERT INTO utilisateur VALUES (1,'coyote@mail.acme',MD5('bipbip'),'Coyote',NOW()),"
                            + "(2,'jadorejquery@unefois.be',MD5('avecdesfrites'),'Thunderseb',NOW());");
            preparedStatement.executeUpdate();
            connexion.commit();
            log.info("Insertion d'un jeu de données dans la table bdd_sdzee_test.utilisateur réussie!");
        } catch (SQLException e) {
            rollback(connexion);
            throw new DAOException(e);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {

        log.info("Fin du test n° " + nbTest);

        /* Connexion à la base de données */
        try {
            /* Récupération de la connexion à la bdd de développement depuis la Factory */
            connexion = daoFactory.getConnection();

            /* Suppression des données */
            log.trace("Tentative de suppression des données de la table bdd_sdzee_test.utilisateur.");
            preparedStatement = connexion.prepareStatement("DELETE FROM utilisateur;");
            preparedStatement.executeUpdate();
            connexion.commit();
            log.info("Suppression des données de la table bdd_sdzee_test.utilisateur réussie!");
        } catch (SQLException e) {
            rollback(connexion);
            throw new DAOException(e);
        }

        log.info("Fin du test n° " + nbTest);
    }

    @Test
    public void testTrouverOK() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "coyote@mail.acme";
        Utilisateur utilisateur = utilisateurDao.trouver(email);
        assertEquals("l'email mentionné en paramètre correspond à l'email récupéré en bdd.", email,
                utilisateur.getEmail());
    }

    @Test
    public void testTrouverKO() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "bidon@bidon.bidon";
        Utilisateur utilisateur = utilisateurDao.trouver(email);
        assertNull(utilisateur);
    }

    @Test(expected = NullPointerException.class)
    public void testTrouverException() {
        /* Doit retourner une exception étant donné que le DAOFactory en paramètre est null */
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(null);
        String email = "coyote@mail.acme";
        Utilisateur utilisateur = utilisateurDao.trouver(email);
    }

    @Test
    public void testAuthentifierOK() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "coyote@mail.acme";
        String motDePasse = "bipbip";
        Utilisateur utilisateur = utilisateurDao.authentifier(email, motDePasse);
        assertEquals("Une correspondance email et mot de passe a bien été trouvé en bdd.", email,
                utilisateur.getEmail());
    }

    @Test
    public void testAuthentifierKO() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "bidon";
        String motDePasse = "bidon";
        Utilisateur utilisateur = utilisateurDao.authentifier(email, motDePasse);
        assertNull(utilisateur);
    }

    @Test
    public void testCreerOK() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "unEmailBidon@test.fr";
        String motDePasse = "unMotDePasseBidon";
        String nom = "unNomBidon";
        Utilisateur userAdd = new Utilisateur();
        userAdd.setEmail(email);
        userAdd.setMotDePasse(motDePasse);
        userAdd.setNom(nom);

        /* Création d'un utilisateur une première fois */
        utilisateurDao.creer(userAdd);
        Utilisateur userFind = utilisateurDao.trouver(email);
        assertEquals("Un nouvel utilisateur a bien été ajouté.", email, userFind.getEmail());

        /* Création du même utilisateur une seconde fois : doit nous retourner une exception = duplicate */
    }

    @Test(expected = DAOException.class)
    public void testCreerKO() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "coyote@mail.acme";
        String motDePasse = "unMotDePasseBidon";
        String nom = "unNomBidon";
        Utilisateur userAdd = new Utilisateur();
        userAdd.setEmail(email);
        userAdd.setMotDePasse(motDePasse);
        userAdd.setNom(nom);

        /*
         * Création d'un utilisateur avec un email déjà existant, doit lancer une exception : Duplicate entry
         * 'coyote@mail.acme' for key 'email'
         */
        utilisateurDao.creer(userAdd);
    }

}
