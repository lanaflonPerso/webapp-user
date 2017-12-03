package tr.exemple.demo.dao;

import org.apache.log4j.Logger;

public class DAOConfigurationException extends RuntimeException {

    // log4j
    final static Logger log = Logger.getLogger(DAOConfigurationException.class);

    /*
     * Constructeurs
     */
    public DAOConfigurationException(String message) {
        // super(message);
        log.error(message);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        // super(message, cause);
        log.error(message, cause);
    }

    public DAOConfigurationException(Throwable cause) {
        // super(cause);
        log.error(cause);
    }
}