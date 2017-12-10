package tr.exemple.demo.dao;

import org.apache.log4j.Logger;

/**
 * Classe qui gère les exceptions liées au DAO
 *
 */
public class DAOException extends RuntimeException {

    /* log4j */
    final static Logger log = Logger.getLogger(DAOException.class);

    /**
     * Affiche dans la log le message d'erreur cité en paramètre
     * 
     * @param message
     *            d'erreur personnalisé
     */
    public DAOException(String message) {
        log.error(message);
    }

    /**
     * Affiche dans la log le message et la cause de l'exception, tous deux cités en paramètre
     * 
     * @param message
     *            d'erreur personnalisé
     * @param cause
     *            de l'exception levée
     */
    public DAOException(String message, Throwable cause) {
        log.error(message, cause);
    }

    /**
     * Affiche dans la log la cause de l'exception cité en paramètre
     * 
     * @param cause
     *            de l'exception levée
     */
    public DAOException(Throwable cause) {
        log.error(cause);
    }
}