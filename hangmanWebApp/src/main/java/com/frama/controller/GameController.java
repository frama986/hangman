package com.frama.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frama.data.Words;
import com.frama.model.ErrorInfo;
import com.frama.model.GameModel;
import com.frama.model.VerificationModel;
import com.frama.model.VerificationResponseModel;

@RestController
public class GameController {

   private static final Logger logger = LoggerFactory.getLogger(GameController.class);

   private final static Integer attempts = 6;
   
   private final static String cookieKey = "HMGameModel";

   /**
    * Starts a new fresh game and saves it using cookies.
    * 
    * @param request http request
    * @param response http response
    * @return the game model
    */
   @RequestMapping(value="/newgame", method=RequestMethod.POST)
   public GameModel newGame(HttpServletRequest request, HttpServletResponse response) {
      logger.debug("[newGame] Request manager.");
      GameModel gm = generateGameModel(request, true);
      saveGameCookie(gm, response);
      return gm;
   }
   
   /**
    * Loads an existing game from cookies or session,
    * if it is not present creates a new game.
    * 
    * @param gameModelString
    * @param request
    * @param response
    * @return the game model
    */
   @RequestMapping(value="/loadgame", method=RequestMethod.POST)
   public GameModel loadGame(
         @CookieValue(value = cookieKey, defaultValue="") String gameModelString, HttpServletRequest request, HttpServletResponse response) {
      
      logger.debug("[loadGame] Request manager.");

      GameModel gm = loadFromCookie(gameModelString, response);
      if(gm != null) {
         request.getSession().setAttribute("gameModel", gm);
         return gm;
      }

      return generateGameModel(request, false);
   }

   /**
    * Checks if the letter belongs to the word to guess
    * and then saves the new game status using cookies.
    * 
    * @param vm object containing the letter
    * @param request http request
    * @param response http response
    * @return object with the returns information, plus the updated game model
    * @throws Exception
    */
   @RequestMapping(value="/guess", method=RequestMethod.POST)
   public VerificationResponseModel guessTheLetter(
         @RequestBody VerificationModel vm, HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      logger.debug("[guessTheLetter] Request manager.");
      
      VerificationResponseModel vrm = wordProcessing(vm, request);
      saveGameCookie(vrm.getModel(), response);
      
      return vrm;
   }
   
   /**
    * Handles the exceptions for RESTful request.
    * It logs the error and returns an object containing the information to be displayed.
    * @param ex the raised exception
    * @return error object
    */
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public ErrorInfo handleAllException(Exception ex) {
      logger.error("[handleAllException] An error occurred.", ex);
      
      ErrorInfo ei = new ErrorInfo("1000", "An error occurred");

      return ei;
   }

   /**
    * Loads or creates a new game model.
    * @param request http request
    * @param isNew if true forces the creation of a new game model
    * @return the game model
    */
   private GameModel generateGameModel(HttpServletRequest request, Boolean isNew) {

      logger.debug("[generateGameModel] Generation of the game model");

      HttpSession session = request.getSession();
      GameModel gm = (GameModel) session.getAttribute("gameModel");

      if(isNew || gm == null || isSolved(gm) || isGameOver(gm)) {
         logger.debug("[generateGameModel] Creation of new game model");
         gm = new GameModel(Words.getRandomWord(), attempts);
         session.setAttribute("gameModel", gm);
      }

      return gm;
   }

   /**
    * Tests the letter and updates the model accordingly with the game status.
    * @param vm object containing the letter
    * @param request http request
    * @return object with the returns information, plus the updated game model
    * @throws Exception 
    */
   private VerificationResponseModel wordProcessing(VerificationModel vm, HttpServletRequest request) throws Exception {

      String letter = vm.getLetter();

      if(letterValidation(letter) != true) {
         logger.warn("[wordProcessing] Invalid letter {}", letter);
         throw new Exception("Exception : Invalid letter " + letter);
      }

      logger.debug("[wordProcessing] Identification of letter {}", letter);

      HttpSession session = request.getSession();

      GameModel gm = (GameModel) session.getAttribute("gameModel");

      Boolean outcome = letterDetection(letter, gm);

      if(outcome == false) {
         logger.debug("[wordProcessing] Letter {} not found", letter);
         gm.increaseErrors();
         gm.decreaseAttempts();
         gm.updateMisses(letter);
      }
      else {
         logger.debug("[wordProcessing] Letter {} found", letter);
         gm.updateGuesses(letter);
      }

      Boolean solved = isSolved(gm);
      Boolean gameOver = (isGameOver(gm) || solved);

      VerificationResponseModel vrm = new VerificationResponseModel(); 
      vrm.setModel(gm);
      vrm.setLetter(letter);
      vrm.setOutcome(outcome);
      vrm.setSolved(solved);
      vrm.setGameOver(gameOver);

      return vrm;
   }

   /**
    * Validates the letter.
    * @param letter the letter
    * @return true if letter is valid, false otherwise
    */
   private Boolean letterValidation(String letter) {
      return letter.matches("[A-Z]{1}");
   }

   /**
    * Checks if the letter belongs to the word to guess.
    * @param letter the letter
    * @param gm the game model
    * @return true if the letter belongs to the word, false otherwise
    */
   private Boolean letterDetection(String letter, GameModel gm) {

      String word  = gm.getWordToGuess();
      String[] hw = gm.getHiddenWord();

      int index   = 0;
      Boolean outcome = false;

      while(index <= word.length()) {
         index = word.indexOf(letter, index);
         if(index == -1)
            break;

         hw[index] = letter;

         index++;
         outcome = true;
      }

      return outcome;
   }

   /**
    * Checks if the game is over (no more attempts).
    * @param gm the game model
    * @return true if the game is over, false otherwise
    */
   private Boolean isGameOver(GameModel gm) {
      if(gm.getAttempts() < 1)
         return true;
      else return false;
   }

   /**
    * Checks if the game is solved (resolved the hidden word).
    * 
    * @param gm the game model
    * @return true if the game is solved, false otherwise
    */
   private Boolean isSolved(GameModel gm) {
      String[] hwa = gm.getHiddenWord();
      for(String w : hwa) {
         if(w.equals("_"))
            return false;
      }
      return true;
   }

   /**
    * Saves the game status using cookies.
    * 
    * @param gm the game model to be saved
    * @param response http response
    */
   private void saveGameCookie(GameModel gm, HttpServletResponse response) {

      ObjectMapper mapper = new ObjectMapper();
      String gameModelString;
      try {
         gameModelString = mapper.writeValueAsString(gm);
         Cookie cookie = new Cookie(cookieKey, gameModelString);
         cookie.setPath("/");
         cookie.setMaxAge(86400);
         response.addCookie(cookie);
      } catch (JsonProcessingException e) {
         logger.error("[saveGameWithCookie] Error during cookie generation", e);
      }
   }

   /**
    * Loads the game status from cookies.
    * 
    * @param gameModelString the value of cookie 
    * @param response http response
    * @return the game model
    */
   private GameModel loadFromCookie(String gameModelString, HttpServletResponse response) {
      
      if(gameModelString == null || gameModelString == "")
         return null;
      
      ObjectMapper mapper = new ObjectMapper();
      try {
         return mapper.readValue(gameModelString, GameModel.class);
      } catch (IOException e) {
         logger.error("[loadFromCookie] Error during cookie fetching", e);
      }
      return null;
   }
}
