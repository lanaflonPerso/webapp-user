<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" action="login.php" id="formLogin">
	<div class="row">
    	<div class="form-group">
        	<input class="form-control" id="user" name="user" placeholder="Adresse e-mail" type="email" required>   
            <div class="alert alert-danger connexion-error" id="login-user-control" >Login ou mot de passe incorrect</div>
        </div>
        <div class="form-group">
        	<input class="form-control" id="loginMdp" name="loginMdp" placeholder="Mot de passe" type="password" required>  
            <div class="alert alert-danger connexion-error" id="login-mdp-control" >Login ou mot de passe incorrect</div>
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