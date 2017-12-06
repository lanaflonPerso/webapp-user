package tr.exemple.demo.config;

import org.junit.Test;

public class ConfigExceptionTest {

    /**
     * Lance bien une exception de la classe ConfigException avec un message.
     */
    @Test(expected = ConfigException.class)
    public void testConfigExceptionWithMessage() {
        throw new ConfigException(
                "@testDConfigExceptionWithMessage(): Lance bien une exception de la classe ConfigException avec un message.");
    }

    /**
     * Lance bien une exception de la classe ConfigException avec un message et une cause.
     */
    @Test(expected = ConfigException.class)
    public void testConfigExceptionWithMessageAndCause() {
        try {
            int resultat = 2 / 0;
        } catch (ArithmeticException cause) {
            throw new ConfigException(
                    "@testConfigExceptionWithMessageAndCause(): Lance bien une exception de la classe ConfigException avec un message et une cause.",
                    cause);
        }
    }

    /**
     * Lance bien une exception de la classe ConfigException avec une cause.
     */
    @Test(expected = ConfigException.class)
    public void testConfigExceptionWithCause() {
        try {
            int resultat = 1 / 0;
        } catch (ArithmeticException cause) {
            throw new ConfigException(cause);
        }
    }

}