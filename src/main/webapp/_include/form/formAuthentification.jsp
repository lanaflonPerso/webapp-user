<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" action="ajax" id="formAuthentification">
	<div class="row">
    	<div class="form-group">
        	<input class="form-control" id="formAuthentificationEmail" name="email" placeholder="Adresse e-mail" type="email" required>   
            <div class="alert alert-danger connexion-error" id="formAuthentificationEmailControl" >Email ou mot de passe incorrect</div>
        </div>
        <div class="form-group">
        	<input class="form-control" id="formAuthentificationMotDePasse" name="motDePasse" placeholder="Mot de passe" type="password" required>  
            <div class="alert alert-danger connexion-error" id="formAuthentificationMotDePasseControl" >Email ou mot de passe incorrect</div>
        </div>
        <div class="col-xs-9 form-group connexion-option">
        	<a class="connexion-text" id="impossible" href="#impossibleContent" data-toggle="collapse" >Impossible de se connecter ?</a><br/>
            <a class="connexion-text" id="inscription" href="#inscriptionContent" data-toggle="collapse" >Créer un compte</a>
        </div>
        <div class="col-xs-3 form-group connexion-option" style="padding-top:8px;">
        	<button class="btn pull-right" 
            	id="btnValiderLogin"
                type="submit">
                <i class="fa fa-check"></i>
            </button>
        </div>
	</div>
</form>