package com.frama.data;

import java.util.Random;

public class Words {

   private static String[] words = {"ALLIGATOR", "ANT", "BEAR", "BEE", "BIRD", "CAMEL", "CAT", "CHEETAH", "CHICKEN", "CHIMPANZEE", "COW", "CROCODILE", "DEER", "DOG", "DOLPHIN", "DUCK", "EAGLE", "ELEPHANT", "FISH", "FLY", "FOX", "FROG", "GIRAFFE", "GOAT", "GOLDFISH", "HAMSTER", "HIPPOPOTAMUS", "HORSE", "KANGAROO", "KITTEN", "LION", "LOBSTER", "MONKEY", "OCTOPUS", "OWL", "PANDA", "PIG", "PUPPY", "RABBIT", "RAT", "SCORPION", "SEAL", "SHARK", "SHEEP", "SNAIL", "SNAKE", "SPIDER", "SQUIRREL", "TIGER", "TURTLE", "WOLF", "ZEBRA"};
   
   private static Random random = new Random();
   
   public static String getRandomWord() {
      return words[random.nextInt(words.length)];
   }
}
