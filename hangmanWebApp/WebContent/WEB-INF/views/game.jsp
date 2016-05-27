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
<script src="<c:url value="/resources/js/game.js" />"></script>
</head>
<body>
   <div class="main-container">

      <input type="hidden" name="attempts" id="attempts"
         value="${gameModel.attempts}"> <input type="hidden"
         name="misses" id="misses" value="${gameModel.misses}">
      <input type="hidden" name="errors" id="errors"
         value="${gameModel.errors}"> <input type="hidden"
         name="displayedWord" id="displayedWord"
         value="${gameModel.displayedWord}"> <input
         type="hidden" name="username" id="username" value="${username}">

      <div class="row">
         <div class="row title">
            <div class="cell">Hangman Game</div>
         </div>
      </div>
      <div class="row">
         <div class="cell">Welcome ${username}</div>
      </div>
      <div class="row center">
         <div class="cell">
            Attempts: <span id="attemptsText">${gameModel.attempts}</span>
         </div>
         <div class="cell">
            Errors: <span id="attemptsText">${gameModel.errors}</span>
         </div>
      </div>
      <div class="row center">
         <div class="cell center blue-border">
            <div class="row">
               <div class="cell">What’s the word?</div>
               <div class="cell">
                  <span id="displayedWord">${gameModel.displayedWord}</span>
               </div>
            </div>
            <div class="row"></div>
            <div class="row">
               <div class="cell">
                  <input type="text" name="guessLetter" id="guessLetter"
                     size="1" maxlength="1"
                     class="bottomline two-letter uppercase"
                     autofocus="autofocus">
               </div>
               <div class="cell">
                  <input type="button" id="guessButton" value="Guess!">
               </div>
            </div>
         </div>
      </div>
      <div class="row center">
         <div class="cell center">
            Misses: <span id="missesText">${gameModel.misses}</span>
         </div>
      </div>
   </div>
</body>
</html>