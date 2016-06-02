/**
 * 
 */

var gameModel = null;

$(function() {
   
   initGameModel();
   
   $('#guessLetter').keydown(verifyInput);
});

function initGameModel() {
   var obj = {}
   loadGame(obj);
}

function loadGame(obj) {
   
   $.ajax({
      type : "POST",
      url : "loadgame",
      contentType : "application/json",
      data : JSON.stringify(obj),
      dataType : 'json',
      timeout : 100000,
      success : function(data) {
         initializeModel(data);
      },
      error : function(e) {
         displayError('An error occurred!');
      }
   });
}

function newGame() {
   
   $.ajax({
      type : "POST",
      url : "newgame",
      contentType : "application/json",
      data : "{}",
      dataType : 'json',
      timeout : 100000,
      success : function(data) {
         initializeModel(data);
      },
      error : function(e) {
         displayError('An error occurred!');
      }
   });
}

function initializeModel(data) {
   gameModel = {};
   $.extend(gameModel, data);
   
   updatePageFileds();
}

function updatePageFileds() {
   $('#attempts').html(gameModel.attempts);
   $('#errors').html(gameModel.errors);
   $('#hiddenWord').html(gameModel.hiddenWord.join(" "));
   $('#misses').html(gameModel.misses);
   
   $('#guessLetter').val('');
}

function verifyInput(e) {
   var code = e.keyCode || e.which;
   var chr = $(this).val();

   if(code == 8 || code == 46) {
      $(this).val('');
   }
   else if(code == 13 && chr != "") {
      gessTheWord(chr);
   }
   //65-90
   else if(code >= 65 && code <= 90) {
      var l = String.fromCharCode(code).toUpperCase();
      if(isNewLetter(l))
         $(this).val(l);
      else {
         displayResult('#feedback-already-used');
      }
   }
   
   e.preventDefault();
   e.stopPropagation();
   return null;
}

function isNewLetter(letter) {
   var m = (gameModel.misses.indexOf(letter) < 0);
   var h = ($.inArray(letter, gameModel.hiddenWord) < 0);
   if(m && h)
      return true;
   return false;
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
         manageReturn(data);
      },
      error : function(e) {
         displayError('An error occurred!');
      }
   });
}

/**
 * Manages the return from the letter verification
 * @param data : object {letter, model, outcome}
 */
function manageReturn(data) {
   updateGameModel(data.model);
   if(data.outcome) {
      displayResult('#feedback-ok');
   }
   else {
      displayResult('#feedback-ko');
   }

   if(data.gameOver) {
      var wtg = data.model.wordToGuess;
      revealHiddenWord(wtg);
      if(data.solved) {
         alert('Congratulations! You have guessed the hidden word ' + wtg);
      }
      else {
         alert('Game Over. The hidden word is ' + wtg);
      }
      newGame();
   }
}

function updateGameModel(model) {
   initializeModel(model);
}

function displayResult(id) {
   $('.feedback').hide();
   $(id).show().delay(2000).fadeOut();
}

function displayError(msg) {
   $('#feedback').html(msg);
   $('.feedback').hide(0);
   $('#feedback').show();
}

function revealHiddenWord(wtg) {
   $('#hiddenWord').html(wtg.split('').join(' '));
}