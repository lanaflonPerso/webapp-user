<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" 
            		class="navbar-toggle collapsed" 
            		data-toggle="collapse" 
            		data-target="#navbar"
                    aria-expanded="false" 
                    aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value="index.jsp"/>">${application.name}</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="<c:url value="home.jsp"/>">Home</a></li>
                <li><a href="" id="btnLogin" data-toggle="modal" data-target="#modalLogin"><i class="fa fa-user"></i> CONNEXION</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>