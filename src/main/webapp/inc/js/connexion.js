/**
 * Fichier javascript lié à la connexion d'un utilisateur (authentification, mot de passe oublié et inscription).
 * 
 * Il sert à envoyer les données des formulaires saisis et validé par le visiteur au format JSON via AJAX vers la servlet Ajax.java.
 * Cette servlet retourne une reponse au format JSON qui sera affiché à l'utilisateur. 
 */

$("form#formAuthentification").submit('click', function(event){
	event.preventDefault(); //disable the default form submission
	
	/* Récupère les valeurs de chaque champ du formulaire saisies par le visiteur */
	var email = $('#formAuthentificationEmail').val();
	var motDePasse = $('#formAuthentificationMotDePasse').val();
	var request = { email:email, motDePasse:motDePasse };
	
	/* afficher un spinner en attendant que le résultat s'affiche */
	$('#resultatAuthentification').html('<div align=center><img src="http://www.mediaforma.com/sdz/jquery/ajax-loader.gif"></div>').show("slow");
	
	/* envoi et récupération des données avec la méthode 'POST' d'Ajax */
	$.post(
		'ajax', // url de la servlet qui va récupérer les données envoyées, c'est le paramètre "request" dans la servlet.
		request, // les données du formulaire à transmettre à la servlet au format.
		function(response){ // gestion de la réponse retournée par la servlet. paramètre "response" dans la servlet.
			$('#resultatAuthentification').html(response).show("slow").delay(10000).hide("slow");
		}
	);
	
	/* envoi et récupération des données avec la méthode 'POST' d'Ajax */
//	$.ajax({
//		url: "ajax",
//		type: 'POST',
//		dataType: 'json',
//		data: JSON.stringify(utilisateur), // les données du formulaire à transmettre à la servlet au format JSON
//		contentType: 'application/json', 
//		mimeType: 'application/json',
//		
//		success: function(data){ // gestion de la réponse retournée par la servlet. paramètre "response" dans la servlet.
//			$('#resultatLogin').html(data).show("slow").delay(10000).hide("slow");
//		},
//		error: function(data, status, er){
//			alert("error: " + data + " status: " + status + " er: " + er);
//		}
//	});
	
});