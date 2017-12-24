package tr.exemple.utilitaires;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author thra
 *
 */
public class UtilitaireTest {

    final static Logger log = Logger.getLogger(UtilitaireTest.class);

    /**
     * Test method for {@link tr.exemple.utilitaires.UtilitaireImpl#getOS()}.
     */
    @Test
    public void testGetOS() {
        assertNotNull(Utilitaire.getOS());
    }

}
