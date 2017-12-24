package tr.exemple.utilitaires;

import org.apache.log4j.Logger;

/**
 * Classe qui gère les exceptions liées au package tr.exemple.utilitaires
 *
 */
public class UtilitaireException extends RuntimeException {

    /* log4j */
    final static Logger log = Logger.getLogger(UtilitaireException.class);

    /**
     * Affiche dans la log le message d'erreur cité en paramètre
     * 
     * @param message
     *            d'erreur personnalisé
     */
    public UtilitaireException(String message) {
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
    public UtilitaireException(String message, Throwable cause) {
        log.error(message, cause);
    }

    /**
     * Affiche dans la log la cause de l'exception cité en paramètre
     * 
     * @param cause
     *            de l'exception levée
     */
    public UtilitaireException(Throwable cause) {
        log.error(cause);
    }
}