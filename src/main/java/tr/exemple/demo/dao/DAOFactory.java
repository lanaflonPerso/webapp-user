package tr.exemple.demo.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import tr.exemple.demo.Main;

public class DAOFactory {

    private static final String FICHIER_PROPERTIES       = "/tr/exemple/demo/dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";
    
    final static Logger log = Logger.getLogger(DAOFactory.class);

    /* package */BoneCP         connectionPool           = null;

    /* package */ DAOFactory( BoneCP connectionPool ) {
        this.connectionPool = connectionPool;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
    	    	
    	log.trace( "Initialisation du DAOFactory." );
    	    	
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;
        BoneCP connectionPool = null;

        // gestion de notre fichier properties
        log.trace("Ouverture du fichier " + FICHIER_PROPERTIES);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
        
        

        if ( fichierProperties == null ) {
        	log.error( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        } else {
        	log.trace("Le fichier " + FICHIER_PROPERTIES + " a été correctement chargé.");
        }        
        
        try {
        	/* Récupération des champs du fichier properties */
        	log.trace("Récupération de chaque champ du fichier properties.");
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
            log.trace("Tous les champs ont été récupérés.");
        } catch ( FileNotFoundException e ) {
        	log.error( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.",
                    e );
        } catch ( IOException e ) {
        	log.error( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES );
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES,
                    e );
        }

        // Connexion au driver jdbc
        try {
            Class.forName( driver );
            log.trace( "Connexion au driver jdbc : \"" + driver + "\" avec succès" );
        } catch ( ClassNotFoundException e ) {
        	log.error( "Le driver est introuvable dans le classpath." );
            throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        try {
            /*
             * Création d'une configuration de pool de connexions via l'objet
             * BoneCPConfig et les différents setters associés.
             */
        	log.trace( "Création d'une configuration de pool de connexions via l'objet BoneCPConfig et les différents setters associés." );
            BoneCPConfig config = new BoneCPConfig();
            /* Mise en place de l'URL, du nom et du mot de passe */
            config.setJdbcUrl( url );
            config.setUsername( nomUtilisateur );
            config.setPassword( motDePasse );
            /* Paramétrage de la taille du pool */
            config.setMinConnectionsPerPartition( 5 );
            config.setMaxConnectionsPerPartition( 10 );
            config.setPartitionCount( 2 );
            /*
             * Création du pool à partir de la configuration, via l'objet BoneCP
             */
            connectionPool = new BoneCP( config );
        } catch ( SQLException e ) {
        	log.error( "Erreur de configuration du pool de connexions." );
            e.printStackTrace();
            throw new DAOConfigurationException( "Erreur de configuration du pool de connexions.", e );
        }
        /*
         * Enregistrement du pool créé dans une variable d'instance via un appel
         * au constructeur de DAOFactory
         */
        DAOFactory instance = new DAOFactory( connectionPool );
        System.out.println( "Instanciation du DAOFactory" );
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    /* package */Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO (un seul
     * pour le moment)
     */
//    public UtilisateurDao getUtilisateurDao() {
//        return new UtilisateurDaoImpl( this );
//    }
}
