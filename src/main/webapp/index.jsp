<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">

<head>
   	<meta charset="UTF-8">
   	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link type="text/css" rel="stylesheet" href="<c:url value="inc/css/libs/bootstrap.min.css"/>" />
   	<link type="text/css" rel="stylesheet" href="<c:url value="inc/css/prod/global.min.css"/>" />
   	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script type="text/javascript" src="<c:url value="inc/js/libs/jquery.min.js"/>" ></script>
	<script type="text/javascript" src="<c:url value="inc/js/libs/bootstrap.min.js"/>" ></script>
	<title>${project.artifactId} - ${project.version}</title>
</head>

<body>

<c:import url="_include/header.jsp" />

<div id="maven-container" class="container">
    <ul>
        <li>Application : ${application.name}</li>
        <li>Version : ${project.version}</li>
        <li>Date du build : ${maven.build.timestamp}</li>
        <li>Java version : ${java.version}</li>
    </ul>
</div>

<c:import url="_include/footer.jsp" />

<c:import url="_include/modalConnexion.jsp" />
<c:import url="_include/modalTC.jsp" />


	<script type="text/javascript" src="<c:url value="inc/js/prod/production.min.js"/>" ></script>	
</body>

</html>