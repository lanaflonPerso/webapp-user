package tr.exemple.demo.dao;

import static tr.exemple.demo.dao.DAOUtilitaire.fermeturesSilencieuses;
import static tr.exemple.demo.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tr.exemple.demo.beans.Utilisateur;

@DisplayName("Test les méthodes imlémentées de la classe UtilisateurDao ainsi que les résultats des requêtes en bdd.")
public class UtilisateurDaoImplTest extends DBTestCase {

    final static Logger log = Logger.getLogger(UtilisateurDaoImplTest.class);

    final static String TEST_BDD_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    final static String TEST_BDD_CONNECTION_URL = "jdbc:mysql://localhost:3307/bdd_sdzee_test";
    final static String TEST_BDD_USERNAME = "test";
    final static String TEST_BDD_PASSWORD = "test";

    public UtilisateurDaoImplTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, TEST_BDD_DRIVER_CLASS);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, TEST_BDD_CONNECTION_URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, TEST_BDD_USERNAME);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, TEST_BDD_PASSWORD);
    }

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/main/resources/dataset/user.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Test
    @DisplayName("Test que l'email mentionné a bien été trouvé en bdd.")
    public void testTrouverOK() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";
        String email = "coyote@mail.acme";

        try {
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            try {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        assertEquals("l'email mentionné en paramètre correspond à l'email récupéré en bdd.", email,
                utilisateur.getEmail());
    }

    @Test
    @DisplayName("Test que l'email mentionné n'a pas été trouvé en bdd.")
    public void testTrouverKO() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";
        String email = "bidon";

        try {
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            try {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        assertNull(utilisateur);
        Assertions.assertTrue(utilisateur == null, "L'email n'existe pas à bdd et retourne un résultat null.");
    }

    @Test
    @DisplayName("Tentative d'authentification d'un utilisateur en vérifiant que l'email et le mot de passe saisies existent bien en bdd.")
    public void testAuthentifierOK() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        String sql = "" + "SELECT * " + "FROM utilisateur " + "WHERE email = ? AND mot_de_passe = MD5(?)";
        String email = "coyote@mail.acme";
        String motDePasse = "bipbip";

        try {
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            try {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        assertEquals("Une correspondance email et mot de passe a bien été trouvé en bdd.", email,
                utilisateur.getEmail());
    }

    @Test
    @DisplayName("Tentative d'authentification échoué d'un utilisateur en vérifiant que l'email et le mot de passe saisies existent bien en bdd.")
    public void testAuthentifierKO() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        String sql = "" + "SELECT * " + "FROM utilisateur " + "WHERE email = ? AND mot_de_passe = MD5(?)";
        String email = "bidon";
        String motDePasse = "bidon";

        try {
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            try {
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
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        Assertions.assertTrue(utilisateur == null,
                "Une correspondance email et mot de passe n'a pas été trouvé en bdd.");
    }

    @Test
    @DisplayName("Tentative d'ajout d'un nouvel utilisateur dans la bdd.")
    public void testCreerOK() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        String sql = "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES (?, ?, ?, NOW())";
        String email = "unEmailBidon@test.fr";
        String motDePasse = "unMotDePasseBidon";
        String nom = "unNomBidon";
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(motDePasse);
        utilisateur.setNom(nom);

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = DriverManager.getConnection(TEST_BDD_CONNECTION_URL, TEST_BDD_USERNAME, TEST_BDD_PASSWORD);
            preparedStatement = initialisationRequetePreparee(connexion, sql, true, utilisateur.getEmail(),
                    utilisateur.getMotDePasse(), utilisateur.getNom());
            int statut = preparedStatement.executeUpdate();
            log.trace("statut de la requête d'insertion = " + statut);
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                utilisateur.setId(valeursAutoGenerees.getLong(1));
            } else {
                throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        log.trace("id = " + utilisateur.getId());
        // TODO assertion de vérification du premier id autoincrementé avec l'id généré = devrait toujours être égale à
        // 3
    }

    @Test
    @DisplayName("Tentative en échec d'un ajout d'un utilisateur avec un email déjà existant dans la bdd.")
    public void testCreerKO() {
        // TODO devrait lancer une exception de duplication
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le mapping) entre une ligne issue de la table
     * des utilisateurs (un ResultSet) et un bean Utilisateur.
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
