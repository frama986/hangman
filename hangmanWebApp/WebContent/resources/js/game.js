/**
 * Game engine
 */

// Game model
var gameModel = null;

/**
 * On load function.
 */
$(function() {

   initGameModel();

   $('#guessLetter').keydown(verifyInput);
   $('#guessButton').click(guessTheLetter);
   $('#newGameButton').click(newGame);
});

/**
 * Initializes the game.
 */
function initGameModel() {
   var obj = {};
   loadGame(obj);
}

/**
 * Loads the game.
 * @param obj
 */
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
         displayError(e.responseJSON);
      }
   });
}

/**
 * Creates a new game.
 */
function newGame() {

   $('.feedback').hide();
   $('#guessLetter').focus();

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
         displayError(e.responseJSON);
      }
   });
}

/**
 * Initialize the javascript game model.
 * @param data model received from the server
 */
function initializeModel(data) {
   gameModel = {};
   $.extend(gameModel, data);

   updatePageFileds();
}

/**
 * Updates the fields in page.
 */
function updatePageFileds() {
   $('#attempts').html(gameModel.attempts);
   $('#errors').html(gameModel.errors);
   $('#hiddenWord').html(gameModel.hiddenWord.join(" "));
   $('#misses').html(gameModel.misses);

   $('#guessLetter').val('');
}

/**
 * Verifies the input character before being written in the text field.
 * If the character is ok writes the character.
 * @param e the keydown event
 * @returns
 */
function verifyInput(e) {
   var code = e.keyCode || e.which;

   // Delete or Backspace
   if(code == 8 || code == 46) {
      $(this).val('');
   }
   // Enter
   else if(code == 13) {
      guessTheLetter();
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

/**
 * If the input fields is not null submits the letter.
 */
function guessTheLetter() {
   var chr = $('#guessLetter').val();
   
   if(chr != null && chr != '') {
      submitLetter(chr);
   }
   // Restore focus on input field
   $('#guessLetter').focus();
}

/**
 * Checks if the letter has already been used.
 * @param letter
 * @returns {Boolean}
 */
function isNewLetter(letter) {
   var m = (gameModel.misses.indexOf(letter) < 0);
   var h = ($.inArray(letter, gameModel.hiddenWord) < 0);
   if(m && h)
      return true;
   return false;
}

/**
 * Submit the letter.
 * @param letter
 */
function submitLetter(letter) {

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
         displayError(e.responseJSON);
      }
   });
}

/**
 * Manages the return from the letter verification.
 * @param data : object {letter, model, outcome}
 */
function manageReturn(data) {
   updateGameModel(data.model);
   if(data.outcome) {
      lightBorder('green');
      displayResult('#feedback-ok');
   }
   else {
      lightBorder('red');
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

/**
 * Updates the game model after status has changed.
 * @param model
 */
function updateGameModel(model) {
   initializeModel(model);
}

/**
 * Displays the outcome of request.
 * @param id
 */
function displayResult(id) {
   $('.feedback').hide();
   $(id).show()
   .delay(2000)
   .fadeOut('fast');
}

/**
 * Displays errors messages.
 * @param response
 */
function displayError(response) {
   lightBorder('red');
   var errMsg = response.errCode
   + " - System Error : "
   + response.errMsg
   + " at "
   + response.errTime;

   $('.feedback').hide();
   $('#feedback').html(errMsg);
   $('#feedback').show();
}

/**
 * Shows the word to guess.
 * @param wtg
 */
function revealHiddenWord(wtg) {
   $('#hiddenWord').html(wtg.split('').join(' '));
}

/**
 * Highlights the borders.
 * @param color
 */
function lightBorder(color){
   $('#central-container').addClass(color+'-border-highlight');
   window.setTimeout(function() {
      $('#central-container').removeClass(color+'-border-highlight');
   }, 2000);
}