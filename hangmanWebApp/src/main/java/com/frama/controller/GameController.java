package com.frama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.frama.model.GameModel;
import com.frama.model.VerificationModel;
import com.frama.model.VerificationResponseModel;

@RestController
public class GameController {
   
   private static final Logger logger = LoggerFactory.getLogger(GameController.class);

   @RequestMapping(value="/guess", method=RequestMethod.POST)
   public VerificationResponseModel verifyLetter(@RequestBody VerificationModel vm, HttpServletRequest request) {
      
      String letter = vm.getLetter();
      
      logger.debug("[verifyLetter] Verification of letter {}", letter);
      
      HttpSession session = request.getSession();
      
      GameModel gm = (GameModel) session.getAttribute("gameModel");
      
      VerificationResponseModel vrm = new VerificationResponseModel(); 
      vrm.setModel(gm);
      vrm.setLetter(letter);
      vrm.setOutcome((gm.getWordToGuess().indexOf(letter) >= 0));
      
      return vrm;
   }
}
