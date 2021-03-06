<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<title>Hangman Game</title>

<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<script src="<c:url value="/resources/lib/jquery-1.12.4.min.js" />"></script>
<script src="<c:url value="/resources/js/welcome.js" />"></script>
<script src="<c:url value="/resources/js/tools.js" />"></script>
</head>
<body>
   <div class="main-container">
      <div class="row">
         <div class="row title">
            <div class="cell">Hangman Game</div>
         </div>
      </div>
      <div class="row"></div>
      <div class="login-form">
         <form action="login" method="post">
            <div class="row center">
               <div class="cell">Write your name:</div>
               <div class="cell">
                  <input type="text" name="username" value=""
                     class="bottomline" autofocus="autofocus">
               </div>
            </div>
            <div class="row center">
               <div class="cell">
                  <input type="submit" value="Enter">
               </div>
            </div>
         </form>
      </div>
   </div>
</body>
</html>