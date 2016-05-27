/**
 * 
 */

var gameModel = {};

$(function() {
   
   initGameModel();
   
   $('#guessLetter').keydown(verifyInput);
});

function initGameModel() {

   gameModel = {

         wordToGuess : "", 
         wordSize : "",
         attempts : "",
         errors : "",
         displayedWord : "",
         misses : "",
         hits : ""
   };
}



function verifyInput(e) {
   var code = e.keyCode || e.which;
   var chr = $(this).val();

   if(code == 13 && chr != "") {
      // submit the letter
      alert('submit');
      return;
   }
   //65-90
   else if(code >= 65 && code <= 90) {
      $(this).val(String.fromCharCode(code).toUpperCase()); 
   }
   
   e.preventDefault();
   e.stopPropagation();
   return null;
}