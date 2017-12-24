package tr.exemple.utilitaires;

/**
 * Classe qui fournit toutes méthodes utilitaires de l'application
 * 
 * @author thra
 *
 */
public class Utilitaire {

    /**
     * Retourne le système d'exploitation utilisé par l'application sous la forme "windows 10"
     * 
     * @return le nom du système d'exploitation en petit caractère.
     * @throws UtilitaireException
     */
    public static String getOS() throws UtilitaireException {
        return System.getProperty("os.name").toLowerCase();
    }
}
