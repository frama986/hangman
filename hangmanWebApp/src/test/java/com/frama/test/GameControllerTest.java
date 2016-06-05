package com.frama.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.frama.controller.GameController;
import com.frama.model.GameModel;

/**
 * Class for unit test of Game Controller.
 * This class tests all principal status changes due to submission of a letter giving a specific game model.
 * Other possible test cases can be on:
 *  - creating, saving and loading of the game model
 *  - testing letter validation with a range of possible character submission
 */
public class GameControllerTest {

   private MockMvc mockMvc;

   @Before
   public void setup() {
      // Registers the controller to be tested
      mockMvc = MockMvcBuilders.standaloneSetup(new GameController()).build();
   }

   /**
    * Test the guessed letter case.
    * @throws Exception
    */
   @Test
   public void testGuessTheLetter_guess() throws Exception {

      GameModel gm = new GameModel("BRIDGE", 6);

      mockMvc.perform(post("/guess")
            .contentType(MediaType.APPLICATION_JSON)
            .sessionAttr("gameModel", gm)
            .content("{\"letter\":\"B\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.letter").value("B"))
      .andExpect(jsonPath("$.outcome").value(true))
      .andExpect(jsonPath("$.model.attempts").value(6))
      .andExpect(jsonPath("$.model.errors").value(0))
      .andExpect(jsonPath("$.model.guesses").value("B"))
      .andExpect(jsonPath("$.model.misses").value(""))
      .andExpect(jsonPath("$.gameOver").value(false))
      .andExpect(jsonPath("$.solved").value(false));
   }

   /**
    * Test the missed letter case.
    * @throws Exception
    */
   @Test
   public void testGuessTheLetter_miss() throws Exception {

      GameModel gm = new GameModel("BRIDGE", 6);

      mockMvc.perform(post("/guess")
            .contentType(MediaType.APPLICATION_JSON)
            .sessionAttr("gameModel", gm)
            .content("{\"letter\":\"X\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.letter").value("X"))
      .andExpect(jsonPath("$.outcome").value(false))
      .andExpect(jsonPath("$.model.attempts").value(5))
      .andExpect(jsonPath("$.model.errors").value(1))
      .andExpect(jsonPath("$.model.guesses").value(""))
      .andExpect(jsonPath("$.model.misses").value("X"))
      .andExpect(jsonPath("$.gameOver").value(false))
      .andExpect(jsonPath("$.solved").value(false));
   }

   /**
    * Tests the game over case but not solved.
    * @throws Exception
    */
   @Test
   public void testGuessTheLetter_gameOver() throws Exception {

      GameModel gm = new GameModel("BRIDGE", 1);

      mockMvc.perform(post("/guess")
            .contentType(MediaType.APPLICATION_JSON)
            .sessionAttr("gameModel", gm)
            .content("{\"letter\":\"X\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.letter").value("X"))
      .andExpect(jsonPath("$.outcome").value(false))
      .andExpect(jsonPath("$.model.attempts").value(0))
      .andExpect(jsonPath("$.model.errors").value(1))
      .andExpect(jsonPath("$.model.guesses").value(""))
      .andExpect(jsonPath("$.model.misses").value("X"))
      .andExpect(jsonPath("$.gameOver").value(true))
      .andExpect(jsonPath("$.solved").value(false));
   }

   /**
    * Tests the case in which the game is solved.
    * @throws Exception
    */
   @Test
   public void testGuessTheLetter_solved() throws Exception {

      GameModel gm = new GameModel("BRIDGE", 6, 6, 6, 0, new String[]{"_","R","I","D","G","E"}, "", "R I D G E");

      mockMvc.perform(post("/guess")
            .contentType(MediaType.APPLICATION_JSON)
            .sessionAttr("gameModel", gm)
            .content("{\"letter\":\"B\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.letter").value("B"))
      .andExpect(jsonPath("$.outcome").value(true))
      .andExpect(jsonPath("$.model.attempts").value(6))
      .andExpect(jsonPath("$.model.errors").value(0))
      .andExpect(jsonPath("$.model.guesses").value("R I D G E B"))
      .andExpect(jsonPath("$.model.misses").value(""))
      .andExpect(jsonPath("$.gameOver").value(true))
      .andExpect(jsonPath("$.solved").value(true));
   }

   /**
    * Tests the validation of a wrong letter.
    * @throws Exception
    */
   @Test
   public void testGuessTheLetter_letterValidation() throws Exception {

      GameModel gm = new GameModel("BRIDGE", 6);

      mockMvc.perform(post("/guess")
            .contentType(MediaType.APPLICATION_JSON)
            .sessionAttr("gameModel", gm)
            .content("{\"letter\":\"34\"}"))
      .andExpect(status().isInternalServerError());
   }
}
