package tr.exemple.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe qui sert d'utilitaire au DAO, on y retrouve toutes les méthodes statiques associées au dao.
 * 
 * @author Thierry Raimond
 *
 */
public class DAOUtilitaire {

    /**
     * Initialise la requête préparée basée sur la connexion passée en argument, avec la requête SQL et les objets
     * donnés.
     * 
     * @param connexion
     * @param sql
     * @param returnGeneratedKeys
     * @param objets
     * @return preparedStatement
     * @throws SQLException
     */
    public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql,
            boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }

    /**
     * Effectue un rollback si la connexion n'est pas null
     * 
     * @param connexion
     */
    public static void rollback(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.rollback();
            } catch (SQLException e) {
                throw new DAOException("Impossible d'effectuer un rollback : " + e.getMessage());
            }
        }
    }

    /**
     * Fermeture silencieuse du resultset
     * 
     * @param resultSet
     */
    public static void fermetureSilencieuse(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException("Échec de la fermeture du ResultSet : " + e.getMessage());
            }
        }
    }

    /**
     * Fermeture silencieuse du statement
     * 
     * @param statement
     */
    public static void fermetureSilencieuse(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException("Échec de la fermeture du Statement : " + e.getMessage());
            }
        }
    }

    /**
     * Fermeture silencieuse de la connexion
     * 
     * @param connexion
     */
    public static void fermetureSilencieuse(Connection connexion) {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                throw new DAOException("Échec de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    /**
     * Fermetures silencieuses du statement et de la connexion
     * 
     * @param statement
     * @param connexion
     */
    public static void fermeturesSilencieuses(Statement statement, Connection connexion) {
        fermetureSilencieuse(statement);
        fermetureSilencieuse(connexion);
    }

    /**
     * Fermetures silencieuses du resultset, du statement et de la connexion
     * 
     * @param resultSet
     * @param statement
     * @param connexion
     */
    public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) {
        fermetureSilencieuse(resultSet);
        fermetureSilencieuse(statement);
        fermetureSilencieuse(connexion);
    }
}
