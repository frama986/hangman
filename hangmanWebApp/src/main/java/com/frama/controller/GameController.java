package com.frama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.frama.data.Words;
import com.frama.model.GameModel;
import com.frama.model.VerificationModel;
import com.frama.model.VerificationResponseModel;

@RestController
public class GameController {
   
   private static final Logger logger = LoggerFactory.getLogger(GameController.class);
   
   private static Integer attempts = 6; 
   
   @RequestMapping(value="/newgame", method=RequestMethod.POST)
   public GameModel newGame(HttpServletRequest request) {
      logger.debug("[newGame] Request manager.");
      return generateGameModel(request, true);
   }
   
   @RequestMapping(value="/loadgame", method=RequestMethod.POST)
   public GameModel loadGame(HttpServletRequest request) {
      logger.debug("[loadGame] Request manager.");
      return generateGameModel(request, false);
   }

   @RequestMapping(value="/guess", method=RequestMethod.POST)
   public VerificationResponseModel guessTheLetter(@RequestBody VerificationModel vm, HttpServletRequest request) {
      logger.debug("[guessTheLetter] Request manager.");
      return wordProcessing(vm, request);
   }
   
   /**
    * 
    * @param request
    * @param isNew
    * @return
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
    * 
    * @param vm
    * @param request
    * @return
    */
   private VerificationResponseModel wordProcessing(VerificationModel vm, HttpServletRequest request) {
      
      String letter = vm.getLetter();
      
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
    * 
    * @param letter
    * @param gm
    * @return
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
   
   private Boolean isGameOver(GameModel gm) {
      if(gm.getAttempts() < 1)
         return true;
      else return false;
   }
   
   private Boolean isSolved(GameModel gm) {
      String[] hwa = gm.getHiddenWord();
      for(String w : hwa) {
         if(w.equals("_"))
            return false;
      }
      return true;
   }
}
