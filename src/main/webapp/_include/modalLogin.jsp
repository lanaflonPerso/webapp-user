<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Modal Login -->
<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button id="btnModalLoginClose" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><i class="fa fa-user"></i><b>&nbsp;&nbsp;CONNEXION</b></h4>
            </div>
            <div class="modal-body">
                <div class="resultat-ajax" id="resultatLogin" ></div>

				<c:import url="_include/form/formConnexion.jsp" />

                <div class="row collapse" id="impossibleContent">
                    <hr>
                    <h2><small>Mot de passe oublié ?</small></h2>
                    <div class="resultat-ajax" id="resultatImpossible" ></div>
					
					<c:import url="_include/form/formImpossible.jsp" />
					
                </div>
                <div class="row collapse" id="inscriptionContent">
                    <hr class="colorgraph">
                    <h2><small>Inscription</small></h2>
                    <div class="resultat-ajax" id="resultatInscription" ></div>

					<c:import url="_include/form/formInscription.jsp" />

                </div>
            </div>
        </div>
    </div>
</div>