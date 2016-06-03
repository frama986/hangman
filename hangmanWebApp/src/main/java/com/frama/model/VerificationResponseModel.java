package com.frama.model;

/**
 * Class that maps the return of guess request
 */
public class VerificationResponseModel {
   
   private GameModel model;
   
   private Boolean outcome;
   
   private Boolean gameOver;
   
   private Boolean solved;
   
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

   public Boolean getGameOver() {
      return gameOver;
   }

   public void setGameOver(Boolean gameOver) {
      this.gameOver = gameOver;
   }

   public Boolean getSolved() {
      return solved;
   }

   public void setSolved(Boolean solved) {
      this.solved = solved;
   }

   public String getLetter() {
      return letter;
   }

   public void setLetter(String letter) {
      this.letter = letter;
   }
}
