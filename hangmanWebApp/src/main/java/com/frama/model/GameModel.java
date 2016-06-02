package com.frama.model;

import java.io.Serializable;

public class GameModel implements Serializable{
   
   private static final long serialVersionUID = -4911780008466057971L;

   private String wordToGuess;

   private Integer wordSize;

   private Integer maxAttempts;

   private Integer attempts;

   private Integer errors;

   private String[] hiddenWord;

   private String misses;

   private String guesses;
   
   public GameModel() {}

   public GameModel(String wordToGuess, Integer maxAttempts) {
      this.wordToGuess = wordToGuess;
      this.maxAttempts = maxAttempts;
      this.attempts = maxAttempts;
      this.errors = 0;
      this.misses = "";
      this.guesses = "";
      this.wordSize = this.wordToGuess.length();
      this.hiddenWord = new String[this.wordSize];
      for(int i = 0; i < this.wordSize; i++) {
         this.hiddenWord[i] = "_";
      }
   }

   public GameModel(String wordToGuess, Integer wordSize, Integer maxAttempts, Integer attempts, Integer errors,
         String[] hiddenWord, String misses, String guess) {
      this.wordToGuess = wordToGuess;
      this.wordSize = wordSize;
      this.maxAttempts = maxAttempts;
      this.attempts = attempts;
      this.errors = errors;
      this.hiddenWord = hiddenWord;
      this.misses = misses;
      this.guesses = guess;
   }


   public String getWordToGuess() {
      return wordToGuess;
   }

   public void setWordToGuess(String wordToGuess) {
      this.wordToGuess = wordToGuess;
   }

   public Integer getWordSize() {
      return wordSize;
   }

   public void setWordSize(Integer wordSize) {
      this.wordSize = wordSize;
   }

   public Integer getMaxAttempts() {
      return maxAttempts;
   }

   public void setMaxAttempts(Integer maxAttempts) {
      this.maxAttempts = maxAttempts;
   }

   public Integer getAttempts() {
      return attempts;
   }

   public void setAttempts(Integer attempts) {
      this.attempts = attempts;
   }

   public Integer getErrors() {
      return errors;
   }

   public void setErrors(Integer errors) {
      this.errors = errors;
   }

   public String[] getHiddenWord() {
      return hiddenWord;
   }

   public void setHiddenWord(String[] hiddenWord) {
      this.hiddenWord = hiddenWord;
   }

   public String getMisses() {
      return misses;
   }

   public void setMisses(String misses) {
      this.misses = misses;
   }

   public String getGuesses() {
      return guesses;
   }

   public void setGuesses(String guesses) {
      this.guesses = guesses;
   }
   
   public void increaseErrors() {
      this.errors++;
   }
   
   public void decreaseAttempts() {
      this.attempts--;
   }
   
   public void updateMisses(String letter) {
      if(this.misses.length() > 0)
         this.misses += " ";
      this.misses += letter;
   }
   
   public void updateGuesses(String letter) {
      if(this.guesses.length() > 0)
         this.guesses += " ";
      this.guesses += letter;
   }
   
   public static GameModel resetModel(GameModel thisModel) {
      return new GameModel(thisModel.getWordToGuess(), thisModel.getAttempts());
   }
}
