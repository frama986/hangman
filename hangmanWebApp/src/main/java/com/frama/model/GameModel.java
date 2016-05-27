package com.frama.model;

public class GameModel {
   
   public GameModel() {}
   
   public GameModel(String wordToGuess, Integer maxAttempts) {
      this.wordToGuess = wordToGuess;
      this.attempts = maxAttempts;
      this.wordSize = wordToGuess.length();
      this.errors = 0;
      this.displayedWord = initDisplayedWord(this.wordSize);
      this.misses = "";
      this.hits = "";
   }
   
   private String wordToGuess;
   
   private Integer wordSize;
   
   private Integer attempts;
   
   private Integer errors;
   
   private String displayedWord;
   
   private String misses;
   
   private String hits;
   
   
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

   public String getDisplayedWord() {
      return displayedWord;
   }

   public void setDisplayedWord(String displayedWord) {
      this.displayedWord = displayedWord;
   }

   public String getMisses() {
      return misses;
   }

   public void setMisses(String misses) {
      this.misses = misses;
   }

   public String getHits() {
      return hits;
   }

   public void setHits(String hits) {
      this.hits = hits;
   }

   private static String initDisplayedWord(int size) {
      String tmp = "";
      
      for(int i = 0; i < size; i++) {
         if(i > 0)
            tmp += " ";
         tmp += "_";
      }
      
      return tmp;
   }
}
