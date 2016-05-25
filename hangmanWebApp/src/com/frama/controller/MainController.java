package com.frama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
   
   private static final String VIEW_INDEX = "index";

   @RequestMapping(value="/", method=RequestMethod.GET)
   public String welcome() {
      return VIEW_INDEX;
   }

}
