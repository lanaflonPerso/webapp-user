package tr.exemple.demo.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import tr.exemple.demo.dao.DAOFactory;

public class InitialisationDaoFactory implements ServletContextListener {
    private static final String ATT_DAO_FACTORY = "daofactory";

    private DAOFactory          daoFactory;

    /**
     * Méthode contextInitialized() qui est appelée dès le démarrage de l'application
     * avant le chargement des servlets et filtres du projet
     */
    @Override
    public void contextInitialized( ServletContextEvent event ) {
      	
        /* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        
    	// Fichier properties de log4j
    	PropertyConfigurator.configure("src/main/resources/log4j.properties");
      	System.out.println("Chargement du fichier de configuration log4j.");
        
        /* Instanciation de notre DAOFactory */
        this.daoFactory = DAOFactory.getInstance();
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
        
    }

    @Override
    public void contextDestroyed( ServletContextEvent event ) {
        /* Rien à réaliser lors de la fermeture de l'application... */
    }
}
