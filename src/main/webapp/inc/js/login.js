$("#login").on("click",function(){
	var b=$("#username").val();
	var a=$("#password").val();
	if(b==""){
		$("#messageDiv").css("display","none");
		alert("Username is required");
		return
	}
	if(a==""){
		$("#messageDiv").css("display","none");
		alert("Password is required");
		return
	}
	$.ajax({
		url:"login",
		method:get,
		data:{
			username:b,
			password:a
		},
		success:function(c){
			if(c!=null&&c!=""){
				showMessage(c);
				$("#messageDiv").css("display","block");
			}else{
				$("#messageDiv").css("display","none");
				$("#messageDiv").html("");
				alert("Some exception occurred! Please try again.")
			}
		}
	});
});
$("form#formLogin").submit("click",function(b){
	b.preventDefault();
	var c=$("#user").val();
	var a=$("#loginMdp").val();
	$("#resultatLogin").html('<div align=center><img src="http://www.mediaforma.com/sdz/jquery/ajax-loader.gif"></div>').show("slow");
	$.post("login",{
		username:c,
		password:a
	},
	function(d){
		if(d!=null&&d!=""){
			$("#resultatLogin").html(d).show("slow").delay(10000).hide("slow");
		}else{
			$("#resultatLogin").html("Some exception occured! Please try again").show("slow").delay(10000).hide("slow");
		}
	});
});
function showMessage(a){
	if(a=="SUCCESS"){
		$("#messageDiv").html("<font color='green'>You are successfully logged in. </font>");
	}else{
		if(a=="FAILURE"){
			$("#messageDiv").html("<font color='red'>Username or password incorrect </font>");
		}
	}
};