package com.frama.model;

public class VerificationResponseModel {
   
   private GameModel model;
   
   private Boolean outcome;
   
   private String letter;

   public GameModel getModel() {
      return model;
   }

   public void setModel(GameModel model) {
      this.model = model;
   }

   public Boolean getOutcome() {
      return outcome;
   }

   public void setOutcome(Boolean outcome) {
      this.outcome = outcome;
   }

   public String getLetter() {
      return letter;
   }

   public void setLetter(String letter) {
      this.letter = letter;
   }
   
}
