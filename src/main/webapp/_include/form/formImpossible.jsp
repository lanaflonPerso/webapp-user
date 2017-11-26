<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" action="login.php" id="formImpossible">
	<div class="form-group">
    	<input class="form-control" title="Entrez votre adresse email puis cliquer sur 'envoyer le mot de passe'" id="userImpossible" name="userImpossible" placeholder="Adresse e-mail" type="email" required> 
        <div class="alert alert-danger connexion-error" id="impossible-user-control" >Champ vide ou incorrect</div>
        <div class="form-group connexion-submit">
        	<button class="btn" id="btnValiderImpossible" type="submit">
            	<i class="fa fa-envelope-o"></i>&nbsp Renvoyer mon mot de passe
            </button>
        </div>
    </div>
</form>