package com.frama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.frama.model.GameModel;

@Controller
public class MainController {
   
   private static final Logger logger = LoggerFactory.getLogger(MainController.class);
   
   private static final String VIEW_GAME = "game";
   private static final String VIEW_LOGIN = "login";
   
   /**
    * Main function.
    * Dispatches the first request and checks if user is logged in. 
    * @param model spring model
    * @param request http request
    * @return
    */
   @RequestMapping(value="/", method=RequestMethod.GET)
   public String welcome(ModelMap model, HttpServletRequest request) {
      
      HttpSession session = request.getSession();
      String sessionUser = (String) session.getAttribute("username");
      
      if(sessionUser == null) {
         logger.debug("[welcome] new user found: redirect to login.");
         return VIEW_LOGIN;
      }
      
      logger.debug("[welcome] Username : {}", sessionUser);
      
      GameModel gm = new GameModel("BRIDGE", 6);
      session.setAttribute("gameModel", gm);
      
      model.addAttribute("username", sessionUser);
      model.addAttribute("gameModel", gm);

      return VIEW_GAME;
   }

   /**
    * Login function.
    * Adds the user to session object and redirect to welcome main function.
    * @param username name of user used for the authentication
    * @param request http request
    * @return
    */
   @RequestMapping(value="/login", method=RequestMethod.POST)
   public RedirectView login(@ModelAttribute("username") String username, HttpServletRequest request) {

      HttpSession session = request.getSession();

      if(username != null && !username.isEmpty()) {
         session.setAttribute("username", username);
         logger.debug("[login] New user : {}", username);
      }

      RedirectView redirectView = new RedirectView();
      redirectView.setContextRelative(true);
      redirectView.setExposeModelAttributes(false);
      redirectView.setUrl("/");
      return redirectView;
   }
}
