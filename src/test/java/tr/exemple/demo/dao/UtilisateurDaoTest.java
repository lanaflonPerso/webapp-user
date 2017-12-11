/**
 * 
 */
package tr.exemple.demo.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author thier
 *
 */
public class UtilisateurDaoTest {

    private static DAOFactory daoFactory;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        daoFactory = DAOFactory.getInstanceDev();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        // DAOFactory daoFactory = DAOFactory.getInstanceDev();
        UtilisateurDao testUtilisateur = new UtilisateurDaoImpl(daoFactory);
    }

}
