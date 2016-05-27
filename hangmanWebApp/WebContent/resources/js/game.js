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
      gessTheWord(chr);
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

function gessTheWord(letter) {
   
   var obj = {letter : letter};
   
   $.ajax({
      type : "POST",
      url : "guess",
      contentType : "application/json",
      data : JSON.stringify(obj),
      dataType : 'json',
      timeout : 100000,
      success : function(data) {
         manageReturn(data)
      },
      error : function(e) {
         $('#feedback').html('Error!');
      }
   });
}

//data {letter, model outcome}
function manageReturn(data) {
   
   if(data.outcome)
      displayResult('Guessed :)');
   else
      displayResult('Not Guessed :(');
}

function displayResult(msg) {
   $('#feedback').html(msg);
}
