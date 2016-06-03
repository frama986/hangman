package com.frama.model;

/**
 * Class maps the guess request body
 */
public class VerificationModel {
   
   private String letter;

   public String getLetter() {
      return letter.toUpperCase();
   }

   public void setLetter(String letter) {
      this.letter = letter;
   }
}
