package tr.exemple.demo.config;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import tr.exemple.demo.dao.DAOFactory;

public class InitialisationDaoFactory implements ServletContextListener {
    private static final String ATT_DAO_FACTORY = "daofactory";
    private static final String LOG4J_PROPERTIES_FILE = "/log4j.properties";

    private DAOFactory daoFactory;

    /**
     * Méthode contextInitialized() qui est appelée dès le démarrage de l'application avant le chargement des servlets
     * et filtres du projet
     */
    @Override
    public void contextInitialized(ServletContextEvent event) throws ConfigException {

        /* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();

        // Tentative de chargement du fichier properties de log4j
        try {
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream(LOG4J_PROPERTIES_FILE));
            PropertyConfigurator.configure(props);
            System.out.println("Chargement du fichier de configuration log4j.");
        } catch (IOException e) {
            throw new ConfigException("Le fichier " + LOG4J_PROPERTIES_FILE + " est introuvable.", e);
        }

        /* Instanciation de notre DAOFactory en mode production */
        this.daoFactory = DAOFactory.getInstance(true);
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        /* Rien à réaliser lors de la fermeture de l'application... */
    }
}
