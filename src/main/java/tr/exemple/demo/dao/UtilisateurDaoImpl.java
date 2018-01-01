package tr.exemple.demo.dao;

import static tr.exemple.demo.dao.DAOUtilitaire.fermeturesSilencieuses;
import static tr.exemple.demo.dao.DAOUtilitaire.initialisationRequetePreparee;
import static tr.exemple.demo.dao.DAOUtilitaire.rollback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tr.exemple.demo.beans.Utilisateur;

/**
 * Implémentation des méthodes définies dans l'interface UtilisateurDao
 * 
 * @author Thierry Raimond
 *
 */
public class UtilisateurDaoImpl implements UtilisateurDao {

    private DAOFactory daoFactory;
    private static final String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM utilisateur WHERE email = ?";
    private static final String SQL_INSERT = "INSERT INTO utilisateur (email, mot_de_passe, nom, date_inscription) VALUES (?, ?, ?, NOW())";

    /**
     * Constructeur
     * 
     * @param daoFactory
     */
    UtilisateurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Retourne un objet de la classe Utilisateur récupérant les informations de l'utilisateur trouvé dans la base de
     * données via l'adresse email citée en paramètre.
     * 
     * @param email
     *            l'email de l'utilisateur de type String à chercher dans la table 'utilisateur'
     * @return un objet de la classe Utilisateur
     */
    @Override
    public Utilisateur trouver(String email) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_EMAIL, false, email);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            if (resultSet.next()) {
                utilisateur = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return utilisateur;
    }

    /**
     * Retourne un objet du bean Utilisateur récupérant les informations de l'utilisateur authentifié, c'est à dire
     * qu'une correspondance existe entre les informations de connexion saisies (email et mot de passe cités en
     * paramètre) avec celles en base de données.
     * 
     * @param email
     *            l'email de l'utilisateur de type String
     * @param motDePasse
     *            le mot de passe de l'utilisateur de type String et cyrpté MD5 en base de données
     * @return un objet du bean Utilisateur récupérant les informations de l'utilisateur authentifié ou null
     */
    @Override
    public Utilisateur authentifier(String email, String motDePasse) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        String sql = "" + "SELECT * " + "FROM utilisateur " + "WHERE email = ? AND mot_de_passe = MD5(?)";

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, sql, false, email, motDePasse);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            if (resultSet.next()) {
                utilisateur = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return utilisateur;
    }

    /**
     * Ajoute un nouvel utilisateur dans la table 'utilisateur' en fonction des informations de l'objet Utilisateur cité
     * en paramètre
     * 
     * @param utilisateur
     *            bean Utilisateur contenant les informations à ajouter à la table 'utilisateur'.
     */
    @Override
    public void creer(Utilisateur utilisateur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(),
                    utilisateur.getMotDePasse(), utilisateur.getNom());
            int statut = preparedStatement.executeUpdate();
            connexion.commit();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                /*
                 * Puis initialisation de la propriété id du bean Utilisateur avec sa valeur
                 */
                utilisateur.setId(valeursAutoGenerees.getLong(1));
            } else {
                throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
            }
        } catch (SQLException e) {
            rollback(connexion);
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
    }

    /**
     * Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table
     * des utilisateurs (un ResultSet) et un bean Utilisateur.
     * 
     * @param resultSet
     *            Une ligne issue de la table 'utilisateur' à convertir en bean Utilisateur
     * @return Conversion du ResultSet en objet de la classe Utilisateur
     * @throws SQLException
     *             lance une exception de type SQLExcception
     */
    private static Utilisateur map(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(resultSet.getLong("id"));
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
        utilisateur.setNom(resultSet.getString("nom"));
        utilisateur.setDateInscription(resultSet.getTimestamp("date_inscription"));
        return utilisateur;
    }
}