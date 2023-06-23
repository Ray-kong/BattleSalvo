package cs3500.pa03.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.EndGameController;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameState;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumerations.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndGameControllerTest {

  GameState state;
  private static final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setup() {
    state = new GameState();
    System.setOut(new PrintStream(outputStreamCaptor));
    state.getP1().setMyShips(new ArrayList<>());
    state.getP2().setMyShips(new ArrayList<>());

  }

  @AfterAll
  static void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void endGameSequenceDraw() {
    EndGameController.endGameSequence(state);
    assertEquals("The game resulted in a Draw\n"
        + "Salvo ended in mutual destruction.\n", outputStreamCaptor.toString());
  }

  @Test
  void endGameSequenceP1Loss() {
    List<Ship> ships = new ArrayList<>();

    ships.add(new Ship(
        new ArrayList<>(Arrays.asList(
            new Coord(0, 0))), ShipType.SUBMARINE));

    state.getP2().setMyShips(ships);

    EndGameController.endGameSequence(state);
    assertEquals("The game resulted in a Loss\n"
        + "Salvo ended in your destruction.\n", outputStreamCaptor.toString());
  }
}