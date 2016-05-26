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
</head>
<body>
   <div class="main-container">
      <div class="heder">
         <div class="row">
            <div class="cell">
               <span class="title">Hangman Game</span>
            </div>
            <div class="cell">Welcome ${username}</div>
         </div>
      </div>
      <div class="body">
         <div class="row">
            <div class="cell"></div>
         </div>
         <div class="row">
            <div class="cell left">
               Attempts: <input type="text" id="remAttempts"
                  class="invisible" value="${remAttempts}">
            </div>
            <div class="cell right">
               Errors: <input type="text" id="usedAttemps"
                  class="invisible" value="${usedAttemps}">
            </div>
         </div>
         <div class="row center">
            <div class="cell">What’s the word?</div>
         </div>
         <div class="row center">
            <div class="cell">_ _ _ _ _ _ _</div>
         </div>
         <div class="row">
            <div class="cell">
               Guess: <input type="text" id="guess" class="invisible"
                  value="${guess}">
            </div>
            <div class="cell">
               Misses: <input type="text" id="misses" class="invisible"
                  value="${misses}">
            </div>
         </div>
      </div>
   </div>

</body>
</html>