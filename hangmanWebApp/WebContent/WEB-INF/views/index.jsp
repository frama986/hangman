<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Hangman Game</title>
      
      <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
      <script src="<c:url value="/resources/lib/jquery-1.12.4.min.js" />"></script>
      <script src="<c:url value="/resources/js/welcome.js" />"></script>
   </head>
<body>
   <div class="main-container">
      <div class="login-form">
         <form action="">
            <div>Write your name:</div>
            <div><input type="text" name="userName" value=""></div>
            <div><input type="submit" value="Enter"></div>
         </form>
      </div>
   </div>
</body>
</html>