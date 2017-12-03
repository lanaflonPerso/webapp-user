package tr.exemple.demo.dao;

import org.junit.Test;

public class DAOConfigurationExecptionTest {

    /**
     * Lance bien une exception de la classe DAOConfigurationException avec un message.
     */
    @Test(expected = DAOConfigurationException.class)
    public void testDAOConfigurationExceptionWithMessage() {
        throw new DAOConfigurationException(
                "@testDAOConfigurationExceptionWithMessage(): Lance bien une exception de la classe DAOConfigurationException avec un message.");
    }

    /**
     * Lance bien une exception de la classe DAOConfigurationException avec un message et une cause.
     */
    @Test(expected = DAOConfigurationException.class)
    public void testDAOConfigurationExceptionWithMessageAndCause() {
        try {
            int resultat = 2 / 0;
        } catch (ArithmeticException cause) {
            throw new DAOConfigurationException(
                    "@testDAOConfigurationExceptionWithMessageAndCause(): Lance bien une exception de la classe DAOConfigurationException avec un message et une cause.",
                    cause);
        }
    }

    /**
     * Lance bien une exception de la classe DAOConfigurationException avec une cause.
     */
    @Test(expected = DAOConfigurationException.class)
    public void testDAOConfigurationExceptionWithCause() {
        try {
            int resultat = 1 / 0;
        } catch (ArithmeticException cause) {
            throw new DAOConfigurationException(cause);
        }
    }

}
