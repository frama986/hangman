package com.frama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {
   
   private static final String VIEW_INDEX = "index";
   private static final String VIEW_LOGIN = "login";

   @RequestMapping(value="/", method=RequestMethod.GET)
   public String welcome(ModelMap model, HttpServletRequest request) {
      
      HttpSession session = request.getSession();
      String sessionUser = (String) session.getAttribute("username");
      
      if(sessionUser == null) {
         return VIEW_LOGIN;
      }
      
      model.addAttribute("username", sessionUser);

      return VIEW_INDEX;
   }


   @RequestMapping(value="/login", method=RequestMethod.POST)
   public RedirectView login(@ModelAttribute("username") String username, HttpServletRequest request) {

      HttpSession session = request.getSession();

      if(username != null && !username.isEmpty()) {
         session.setAttribute("username", username);
      }

      RedirectView redirectView = new RedirectView();
      redirectView.setContextRelative(true);
      redirectView.setExposeModelAttributes(false);
      redirectView.setUrl("/");
      return redirectView;
   }
}
