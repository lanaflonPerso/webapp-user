/**
 * 
 */
package tr.exemple.demo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tr.exemple.demo.beans.Utilisateur;

/**
 * @author thier
 *
 */
public class UtilisateurDaoTest {

    final static Logger log = Logger.getLogger(UtilisateurDaoTest.class);

    private static DAOFactory daoFactory;

    private static int nbTest;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /* Instanciation du dao de développement */
        daoFactory = DAOFactory.getInstance(false);

        /* Initialisation du nombre de test */
        nbTest = 0;
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        log.info("Fin des tests de la classe UtilitaireDao.java - il y a eu en tout " + nbTest + " tests effectués.");
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        nbTest++;
        log.info("Début du test n° " + nbTest);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
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

    @Test
    public void authentifierOK() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "coyote@mail.acme";
        String motDePasse = "bipbip";
        Utilisateur utilisateur = utilisateurDao.authentifier(email, motDePasse);
        assertEquals("Une correspondance email et mot de passe a bien été trouvé en bdd.", email,
                utilisateur.getEmail());
    }

    @Test
    public void authentifierKO() {
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl(daoFactory);
        String email = "bidon";
        String motDePasse = "bidon";
        Utilisateur utilisateur = utilisateurDao.authentifier(email, motDePasse);
        assertNull(utilisateur);
    }

}
