package tr.exemple.demo.dao;

import tr.exemple.demo.beans.Utilisateur;

public interface UtilisateurDao {

    void creer(Utilisateur utilisateur) throws DAOException;

    Utilisateur trouver(String email) throws DAOException;

    /*
     * Tentative d'authentification d'un utilisateur en vérifiant que l'email et le mot de passe saisies existent bien
     * en bdd.
     */
    Utilisateur authentifier(String email, String motDePasse) throws DAOException;

}