/**
 * fichier javascript utilisant JQuery et ajax pour la connexion des utilisateurs
 */
$("#login").on('click', function(){
	var username = $("#username").val();
    var password = $("#password").val();
    if(username == ""){
    	$('#messageDiv').css("display","none");
        alert("Username is required");
        return;
    }
    if(password == ""){
    	$('#messageDiv').css("display","none");
        alert("Password is required");
        return;
    }
    $.ajax({
    	url : "login",
        method : get,
        data : {
        	username : username,
            password : password
        },
        success : function(results){
        	if(results != null && results != ""){
        		showMessage(results);
                $('#messageDiv').css("display","block");
                }else{
                	$('#messageDiv').css("display","none");
                    $('#messageDiv').html("");
                    alert("Some exception occurred! Please try again.");
                }
        	}
    	});
});

$('form#formLogin').submit('click', function(event){
	
	event.preventDefault(); // disable the default submission
	var username = $('#user').val();
	var password = $('#loginMdp').val();
	
	// le petit spinner de chargement
	$('#resultatLogin').html('<div align=center><img src="http://www.mediaforma.com/sdz/jquery/ajax-loader.gif"></div>').show("slow");
	
	$.post('login', {
		'username' : username,
		'password' : password
	}, function(data) {
		if(data != null && data != "") {
			$('#resultatLogin').html(data).show("slow").delay(10000).hide("slow");
		} else {
			$('#resultatLogin').html("Some exception occured! Please try again").show("slow").delay(10000).hide("slow");
		}
	});	
});

            
//function to display message to the user
function showMessage(results){
  	if(results == 'SUCCESS'){
   		$('#messageDiv').html("<font color='green'>You are successfully logged in. </font>")
    }else if(results == 'FAILURE'){
       	$('#messageDiv').html("<font color='red'>Username or password incorrect </font>")
    }
}