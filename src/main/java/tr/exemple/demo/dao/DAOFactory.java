package tr.exemple.demo.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import tr.exemple.utilitaires.Utilitaire;

/**
 * La DAO Factory (DaoFactory.java) permet d'initialiser le DAO (Data Access Object) en chargeant notamment les drivers
 * nécessaires (ici un driver JDBC MySQL) et se connecte à la base de données. La Factory peut fournir plusieurs DAO
 * (ici, il n'y en a qu'un seul, UtilisateurDao, qui correspond à une table de la base).
 * 
 * Préchargement d'un certain nombre de connexions à la base de données (pool de connexions) via la DataSource BoneCP.
 * Afin d'alléger la charge que le SGBD doit actuellement supporter à chaque requête.
 * 
 * NB: Lorsqu'un pool de connexions est en place, un appel à la méhode connexion.close() ne ferme pas littéralement une
 * connexion, mais la renvoie simplement pool (Gestion efficace des ressources de connexion à la bdd).
 * 
 * La DaoFactory.java change peu. On y ajoute seulement un setAutoCommit(false) aprés la connexion pour que l'on puisse
 * gérer les transactions nous-mêmes.
 */
public class DAOFactory {

    // private static final String FICHIER_PROPERTIES = "/tr/exemple/demo/dao/dao.properties";
    private static final String FICHIER_PROPERTIES = "dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";
    private static final String PROPERTY_SSL = "ssl";
    private static final String PROPERTY_VERIFY_SERVER_CERTIFICATE = "verifyservercertificate";
    private static final String PROPERTY_USE_SSL = "usessl";
    private static final String PROPERTY_REQUIRE_SSL = "requiressl";
    private static final String PROPERTY_NOM_UTILISATEUR_SSL = "nomutilisateurssl";
    private static final String PROPERTY_MOT_DE_PASSE_SSL = "motdepassessl";
    private static final String PROPERTY_TRUST_STORE_PATH = "truststorepath";
    private static final String PROPERTY_KEY_STORE_PATH = "keystorepath";

    final static Logger log = Logger.getLogger(DAOFactory.class);

    /* paramètres de connexion à la bdd de développement pour faire des tests */
    final static String TEST_BDD_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    final static String TEST_BDD_CONNECTION_URL = "jdbc:mysql://localhost:3307/bdd_sdzee_test";
    final static String TEST_BDD_USERNAME = "test";
    final static String TEST_BDD_PASSWORD = "test";

    /* Datasource BoneCp */
    BoneCP connectionPool = null;

    /* Paramètre de connexion classique du driver manager */
    private String url;
    private String username;
    private String password;

    /**
     * Constructeur
     * 
     * Mise en place d'un pool de connexions sur le principe d'une DataSource, objet qui vient remplacer le
     * DriverManager.
     * 
     * @param connectionPool
     *            de type BoneCP
     */
    DAOFactory(BoneCP connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Constructeur sans connectionPool de BoneCP avec l'utilisation du bon vieux DriverManager.
     */
    DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connectionPool = null;
    }

    /**
     * Méthode chargée de récupérer les informations de connexion à la base de données, charger le driver JDBC,
     * configurer le pool de connexions via BoneCP et retourner une instance de la Factory.
     * 
     * <p>
     * En fonction du paramètre mentionné, le DAO de production sera chargé si ce sera celui de développement.
     * </p>
     * <ul>
     * <li><b>Production</b> : utilisation d'un pool de connexions via BoneCP et usage du SSL pour communiquer avec la
     * base de données.</li>
     * <li><b>Développement</b> : connexion classique avec le minimum requis avec l'utilisation du bon vieux
     * DriverManager. Il n'y a pas de gestion de pool de connexions ni de cryptage SSL. Le but étant de faire des tests
     * sur une base de données créée à cette effet.</li>
     * </ul>
     * 
     * @param prod
     *            de type booléen, définit le mode du DAO : <b>true</b> pour production ou <b>false</b> pour
     *            développement
     * @return une instance de la Factory
     * @throws DAOConfigurationException
     *             Lance une exception lié à une erreur de configuration du DAO
     */
    public static DAOFactory getInstance(Boolean prod) throws DAOConfigurationException {

        DAOFactory instance = null;

        /* si prod est vrai alors on initialise le DAOFactory de production */
        if (prod) {
            log.trace("Initialisation du DAOFactory de production.");

            /* Hashmap du couple (clé, valeur) du fichier properties */
            HashMap<String, String> hmap = new HashMap<String, String>();

            /* Connexion Pool de BoneCP initialisée à null */
            BoneCP connectionPool = null;

            /* Charge le fichier properties */
            InputStream fichierProperties = getInputStream(FICHIER_PROPERTIES);
            // FileInputStream fichierProperties = getFileInputStream(FICHIER_PROPERTIES);

            /* Charge la liste des propriétés du fichier properties dans la map */
            hmap = getPropertiesKeysValues(fichierProperties);

            if (hmap.get(PROPERTY_SSL).contentEquals("true")) {
                SetKeysSystem(hmap);
                log.info("Chargement des clés SSL dans le système.");
            }

            /* Connexion au driver jdbc */
            connexionDriverJdbc(hmap);

            /*
             * Création d'une configuration de pool de connexions via l'objet BoneCPConfig et les différents setters
             * associés.
             */
            connectionPool = connexionBoneCP(hmap);

            /*
             * Enregistrement du pool créé dans une variable d'instance via un appel au constructeur de DAOFactory
             */
            instance = new DAOFactory(connectionPool);
            log.info("Instanciation du DAOFactory et création du pool de connexions : " + instance);
        } else {
            /* Sinon on itialise le DAOFactory de développement */
            log.trace("Tentative d'instanciation du DAO de développement.");
            try {
                Class.forName(TEST_BDD_DRIVER_CLASS);
                log.info("Classe du driver JDBC chargé avec succés!");
            } catch (ClassNotFoundException e) {
                throw new DAOConfigurationException("La classe du driver JDBC n'a pas été trouvé.", e);
            }
            instance = new DAOFactory(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            log.info("Connexion correctement instanciée à la base de données de développement : " + instance);
        }

        return instance;
    }

    /**
     * Méthode chargée de récupérer une connexion à la base de données, en fonction du mode du DAO: <br>
     * Si la variable BoneCP connectionPool n'est pas null alors la connexion utilisée sera celle de production<br>
     * Sinon ce sera celle de développement.
     * 
     * <p>
     * setAutoCommit(false) pour que l'on puisse gérer les transactions nous-mêmes.
     * </p>
     * 
     * @return La connexion à la base de données de type Connection.
     */
    Connection getConnection() {
        Connection connexion = null;
        if (this.connectionPool != null) {
            log.trace("Tentative de connexion à la base de données de production.");
            try {
                connexion = connectionPool.getConnection();
                connexion.setAutoCommit(false);
                log.info("Connexion à la base de données de production réussie : " + connexion);
            } catch (SQLException e) {
                throw new DAOConfigurationException("La connexion à la base de données a échoué.", e);
            }
        } else {
            log.trace("Tentative de connexion à la base de données de développement");
            try {
                connexion = DriverManager.getConnection(url, username, password);
                connexion.setAutoCommit(false);
                log.info("Connexion à la base de données de développement réussie : " + connexion);
            } catch (SQLException e) {
                throw new DAOConfigurationException("La connexion à la base de données a échoué.", e);
            }
        }

        return connexion;
    }

    /**
     * Méthodes de récupération de l'implémentation des différents DAO (un seul pour le moment)
     * 
     * @return implémentation d'UtilisateurDao
     */
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl(this);
    }

    /**
     * Retourne un input stream qui prouve l'existence ou non du fichier Properties cité en paramètre dans le
     * ClassLoader
     * 
     * <p>
     * Lance une exception si le fichier cité en paramètre est introuvable dans le ClassLoader
     * </p>
     * 
     * @param cheminFichierProperties
     *            chemin du fichier Properties
     * 
     * @return un input stream pour lire la resource, ou <tt>null</tt> si la resource n'existe pas.
     */
    protected static InputStream getInputStream(String cheminFichierProperties) {
        /* gestion de notre fichier properties */
        log.trace("Ouverture du fichier " + cheminFichierProperties);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(cheminFichierProperties);
        // InputStream fichierProperties = DAOFactory.class.getResourceAsStream(cheminFichierProperties);
        if (fichierProperties == null) {
            throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
        } else {
            log.info("Le fichier " + FICHIER_PROPERTIES + " a été correctement chargé.");
        }
        return fichierProperties;
    }

    /**
     * Méthode qui retourne un flux de données à partir d'un chemin de fichier cité en paramètre
     * 
     * @param cheminFichierProperties
     *            Chemin du fichier Properties
     * @return FileInputStream flux de données - exemple : java.io.FileInputStream@5f205aa
     */
    protected static FileInputStream getFileInputStream(String cheminFichierProperties) {
        FileInputStream fichierProperties = null;
        try {
            log.trace("Tentative de récupération du fichier : " + cheminFichierProperties);
            fichierProperties = new FileInputStream(cheminFichierProperties);
            log.info("SUCCESS : le fichier " + cheminFichierProperties + " a bien été trouvé.");
        } catch (FileNotFoundException e) {
            throw new DAOConfigurationException(
                    "Le fichier properties " + cheminFichierProperties + " est introuvable.", e);
        }
        return fichierProperties;
    }

    /**
     * Retourne les données du fichier properties cités en paramètre sous la forme d'une HashMap
     * 
     * @param fichierProperties
     *            Flux de données du fichier properties de type FileInputStream
     * @return HashMap Retourne les données du fichier properties sous la forme d'une HashMap
     */
    protected static HashMap<String, String> getPropertiesKeysValues(InputStream fichierProperties) {
        /* Déclaration de la map */
        HashMap<String, String> hmap = new HashMap<String, String>();
        /* Déclaration de l'objet Properties */
        Properties properties = new Properties();

        try {
            /* Récupération des champs du fichier properties */
            log.trace("Récupération de chaque champ du fichier properties.");
            properties.load(fichierProperties);

            /* Ajoute le couple (clé, valeur) de chaque ligne dans la HashMap */
            hmap.put(PROPERTY_DRIVER, properties.getProperty(PROPERTY_DRIVER)); // driver
            hmap.put(PROPERTY_SSL, properties.getProperty(PROPERTY_SSL)); // ssl
            hmap.put(PROPERTY_VERIFY_SERVER_CERTIFICATE, properties.getProperty(PROPERTY_VERIFY_SERVER_CERTIFICATE)); // verifyservercertificate
            hmap.put(PROPERTY_USE_SSL, properties.getProperty(PROPERTY_USE_SSL)); // usessl
            hmap.put(PROPERTY_REQUIRE_SSL, properties.getProperty(PROPERTY_REQUIRE_SSL)); // requiressl
            hmap.put(PROPERTY_NOM_UTILISATEUR_SSL, properties.getProperty(PROPERTY_NOM_UTILISATEUR_SSL)); // nomutilisateurssl
            hmap.put(PROPERTY_MOT_DE_PASSE_SSL, properties.getProperty(PROPERTY_MOT_DE_PASSE_SSL)); // motdepassessl
            /* Si le système d'exploitation est de type Windows */
            if (Utilitaire.getOS().indexOf("win") >= 0) {
                hmap.put(PROPERTY_URL, properties.getProperty("urlwin")); // url
                hmap.put(PROPERTY_TRUST_STORE_PATH, properties.getProperty("truststorepathwin")); // truststorepath
                hmap.put(PROPERTY_KEY_STORE_PATH, properties.getProperty("keystorepathwin")); // keystorepath
                hmap.put(PROPERTY_NOM_UTILISATEUR, properties.getProperty("nomutilisateurwin")); // nomutilisateur
                hmap.put(PROPERTY_MOT_DE_PASSE, properties.getProperty("motdepassewin")); // motdepasse
            } else {
                hmap.put(PROPERTY_URL, properties.getProperty("urlunix")); // url
                hmap.put(PROPERTY_TRUST_STORE_PATH, properties.getProperty("truststorepathunix")); // truststorepath
                hmap.put(PROPERTY_KEY_STORE_PATH, properties.getProperty("keystorepathunix")); // keystorepath
                hmap.put(PROPERTY_NOM_UTILISATEUR, properties.getProperty("nomutilisateurunix")); // nomutilisateur
                hmap.put(PROPERTY_MOT_DE_PASSE, properties.getProperty("motdepasseunix")); // motdepasse
            }
            log.info("Le système d'exploitation est " + Utilitaire.getOS());
            log.info("Tous les champs ont été récupérés.");
            fichierProperties.close();
            log.trace("Fermeture de l'input stream " + fichierProperties);
        } catch (FileNotFoundException e) {
            throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e);
        } catch (IOException e) {
            throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
        }
        return hmap;
    }

    /**
     * Connexion au driver jdbc
     * 
     * @param hmap
     *            Contient les données du fichier properties
     */
    protected static void connexionDriverJdbc(HashMap<String, String> hmap) {
        /* Connexion au driver jdbc */
        try {
            Class.forName(hmap.get(PROPERTY_DRIVER));
            log.info("Connexion au driver jdbc : \"" + hmap.get(PROPERTY_DRIVER) + "\" avec succès");
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
        }
    }

    /**
     * Création d'une configuration de pool de connexions via l'objet BoneCPConfig et les différents setters associés.
     * Ce pool de connexion va pré-initialiser un nombre donné de connexions au SGBD lorsque l'application démarre.
     * Autrement dit, il va créer plusieurs objets Connection et les garder ouvert et bien au chaud.
     * 
     * @param hmap
     *            Contient les données du fichier properties
     * @return BoneCP Création du pool à partir de la configuration, via l'objet BoneCP
     */
    protected static BoneCP connexionBoneCP(HashMap<String, String> hmap) {
        BoneCP connectionPool = null;
        log.trace("Tentative de création d'une configuration de pool de connexions via l'objet BoneCPConfig "
                + "et les différents setters associés.");
        try {
            BoneCPConfig config = new BoneCPConfig();

            if (hmap.get(PROPERTY_SSL).contentEquals("true")) {
                /* Mise en place de la configuration avec SSL */
                config.setJdbcUrl(hmap.get(PROPERTY_URL) + hmap.get(PROPERTY_VERIFY_SERVER_CERTIFICATE)
                        + hmap.get(PROPERTY_USE_SSL) + hmap.get(PROPERTY_REQUIRE_SSL));
                config.setUsername(hmap.get(PROPERTY_NOM_UTILISATEUR_SSL));
                config.setPassword(hmap.get(PROPERTY_MOT_DE_PASSE_SSL));
            } else {
                /* Mise en place de l'URL, du nom et du mot de passe sans SSL */
                config.setJdbcUrl(hmap.get(PROPERTY_URL));
                config.setUsername(hmap.get(PROPERTY_NOM_UTILISATEUR));
                config.setPassword(hmap.get(PROPERTY_MOT_DE_PASSE));
            }

            /* Paramétrage de la taille du pool */
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(2);

            /* Création du pool à partir de la configuration, via l'objet BoneCP */
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            throw new DAOConfigurationException("Erreur de configuration du pool de connexions.", e);
        }
        log.info("Création réussie d'une configuration de pool de connexions : " + connectionPool);
        return connectionPool;
    }

    /**
     * Fournit à l'API JSSE les fichiers keystore et truststore qui seront invoquée par la machine virtuelle java (JVM)
     * lors de l'établissement de la connexion sécurisée. Pour pouvoir fournir ces fichiers à JSSE, il faut déclarer
     * quatre variables système pour la JVM.
     * 
     * @param hmap
     *            Contient les données du fichier properties
     */
    protected static void SetKeysSystem(HashMap<String, String> hmap) {
        System.setProperty("javax.net.ssl.keyStore", hmap.get(PROPERTY_KEY_STORE_PATH));
        System.setProperty("javax.net.ssl.keyStorePassword", hmap.get(PROPERTY_MOT_DE_PASSE_SSL));
        System.setProperty("javax.net.ssl.trustStore", hmap.get(PROPERTY_TRUST_STORE_PATH));
        System.setProperty("javax.net.ssl.trustStorePassword", hmap.get(PROPERTY_MOT_DE_PASSE_SSL));
        // System.setProperty("javax.net.debug", "all");
    }
}
