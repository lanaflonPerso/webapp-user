<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form method="post" action="login.php" id="formInscription">
	<div class="form-group">
    	<input class="form-control" id="userInscription" name="userInscription" placeholder="Adresse e-mail" type="email" required> 
        <div class="alert alert-danger connexion-error" id="inscription-user-control" >Champ vide ou incorrect</div>
    </div>
    <div class="form-group">
    	<input class="form-control" id="inscriptionMdp" name="inscriptionMdp" placeholder="Mot de passe" type="password" required>  
        <div class="alert alert-danger connexion-error" id="inscription-mdp-control" >Champ vide ou incorrect</div>
    </div>
    <div class="form-group">
    	<input class="form-control" id="inscriptionMdpConfirm" name="inscriptionMdpConfirm" placeholder="Confirmer votre mot de passe" type="password" required>    
        <div class="alert alert-danger connexion-error" id="inscription-mdpConfirm-control" >Les mots de passe ne sont pas identiques</div>
    </div>
    <div class="form-group">
		<div class="checkbox" >
			<label>
				<input type="checkbox" name="tc_check"> J'accepte les termes et conditions
			</label>
		</div>
		<div class="alert alert-danger connexion-error" id="inscription-tc-control">Les termes et conditions ne sont pas acceptés</div>
	</div>
	<div class="form-group connexion-text">
    	En cliquant sur <strong class="label label-black">Inscription gratuite</strong>, vous acceptez les <a href="#" data-toggle="modal" data-target="#t_and_c_m">termes et conditions</a> énoncés par ce site, incluant notre politique d'utilisation des Cookies (inexistante).
    </div>
    <div class="form-group connexion-submit" >
    	<button class="btn" id="btnValiderInscription" type="submit">
        	<i class="fa fa-envelope-o"></i>&nbsp Inscription gratuite
        </button>
    </div>
</form>