package tr.exemple.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainTest {

    final static Logger log = Logger.getLogger(MainTest.class);

    private static int before;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        log.trace("je m'éxécute au tout début.");
        before = 0;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        log.trace("je m'éxécute tout à la fin.");
    }

    @Before
    public void setUp() throws Exception {
        before++;
        log.trace("je m'éxécute au début de chaque test. - test n°" + before);
    }

    @After
    public void tearDown() throws Exception {
        log.trace("je m'éxécute à la fin de chaque test.");
    }

    /**
     * Rigourous Test :-)
     */
    @Test
    public void test() {
        assertTrue(true);
        log.trace("@Test Rigouros Test :-)");
    }

    /**
     * Un autre test rigoureux :-)
     */
    @Test
    public void testMain() {
        assertTrue(true);
        log.trace("@Test Un autre test rigoureux :-)");
    }

    @Test
    public void testAdditionner() {
        // PropertyConfigurator.configure("src/main/resources/log4j.properties");
        Main calcul = new Main();
        int a = 1;
        int b = 2;
        int res = calcul.additionner(a, b);
        log.trace("@Test additionner(): " + a + " + " + b + " = " + res);
        assertEquals(res, 3);
    }
}
