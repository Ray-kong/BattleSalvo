package cs3500.pa03.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.PlayerUtils;
import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.GameResult;
import cs3500.pa03.view.ConsoleView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleViewTest {

  private static final PrintStream standardOut = System.out;
  private ByteArrayOutputStream outputStreamCaptor;
  ConsoleView view;
  Coord[][] board1;
  Coord hit;
  Coord empty;
  Coord shiphit;
  Coord miss;
  Coord ship;

  @BeforeEach
  void setUp() {
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
    view = new ConsoleView();
    board1 = new Coord[6][6];
    PlayerUtils.initializeBoard(board1);

    hit = new Coord(0, 0, CoordType.HIT);
    empty = new Coord(0, 0, CoordType.EMPTY);
    shiphit = new Coord(0, 0, CoordType.SHIPHIT);
    miss = new Coord(0, 0, CoordType.MISS);
    ship = new Coord(0, 0, CoordType.SHIP);
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void displayBoard() {
    view.displayBoard(board1);
    byte[] expectedBytes = ("┌─────┬─────┬─────┬─────┬─────┬─────┐\n"
        + "│     │     │     │     │     │     │\n"
        + "├─────┼─────┼─────┼─────┼─────┼─────┤\n"
        + "│     │     │     │     │     │     │\n"
        + "├─────┼─────┼─────┼─────┼─────┼─────┤\n"
        + "│     │     │     │     │     │     │\n"
        + "├─────┼─────┼─────┼─────┼─────┼─────┤\n"
        + "│     │     │     │     │     │     │\n"
        + "├─────┼─────┼─────┼─────┼─────┼─────┤\n"
        + "│     │     │     │     │     │     │\n"
        + "├─────┼─────┼─────┼─────┼─────┼─────┤\n"
        + "│     │     │     │     │     │     │\n"
        + "└─────┴─────┴─────┴─────┴─────┴─────┘\n").getBytes();

    byte[] actualBytes = outputStreamCaptor.toString().getBytes();

    for (int i = 0; i < expectedBytes.length; i++) {
      assertEquals(expectedBytes[i], actualBytes[i]);
    }


  }

  @Test
  void displayCoord() {
    view.displayCoord(hit);
    view.displayCoord(empty);
    view.displayCoord(shiphit);
    view.displayCoord(miss);
    view.displayCoord(ship);

    byte[] expectedBytes = ("  ╳  │     │ ▐╳▌ │  ◌  │ ▐█▌ │").getBytes();
    byte[] actualBytes = outputStreamCaptor.toString().getBytes();

    for (int i = 0; i < expectedBytes.length; i++) {
      assertEquals(expectedBytes[i], actualBytes[i]);
    }
  }

  @Test
  void welcome() {
    view.welcome();
    byte[] expectedBytes = ("\n"
        + "████████████████████████████████████████████████████████████████████\n"
        + "█▄ ▄ ▀██▀  ██ ▄ ▄ █ ▄ ▄ █▄ ▄███▄ ▄▄ ███ ▄▄▄▄██▀  ██▄ ▄███▄ █ ▄█ ▄▄ █\n"
        + "██ ▄ ▀██ ▀ ████ █████ ████ ██▀██ ▄█▀███▄▄▄▄ ██ ▀ ███ ██▀██▄ ▄██ ██ █\n"
        + "█▄▄▄▄██▄▄█▄▄██▄▄▄███▄▄▄██▄▄▄▄▄█▄▄▄▄▄███▄▄▄▄▄█▄▄█▄▄█▄▄▄▄▄███▄███▄▄▄▄█\n"
        + "Directions: Destroy the computer in OOD Battleship.\n"
        + "\n"
        + "Legend:\n"
        + " ╳  = You Hit an Enemy Ship\n"
        + " ◌  = Miss\n"
        + "▐█▌ = Your Ship\n"
        + "▐╳▌ = The Enemy Hit Your Ship\n"
        + "    = Empty\n\n").getBytes();

    byte[] actualBytes = outputStreamCaptor.toString().getBytes();

    for (int i = 0; i < expectedBytes.length; i++) {
      assertEquals(expectedBytes[i], actualBytes[i]);
    }
  }

  @Test
  void promptBoardSize() {
    view.promptBoardSize();
    assertEquals("Please enter dimensions you wish the board to be below [width height].\n"
        + "Board Size: ", outputStreamCaptor.toString());
  }

  @Test
  void invalidBoardSize() {
    view.invalidBoardSize(6, 15);
    assertEquals("I'm sorry, those are invalid dimensions. "
        + "Both must be between 6 and 15. Please retry below.\n"
        + "Board Size: ", outputStreamCaptor.toString());
  }

  @Test
  void promptShips() {
    view.promptShips(6);
    assertEquals("Please enter the number of each type of ship in the order \n"
        + "[Carrier Battleship Destroyer Submarine]. Your fleet size total must \n"
        + "not exceed 6.\n"
        + "Fleet: ", outputStreamCaptor.toString());
  }

  @Test
  void invalidShips() {
    view.invalidShips(6);
    assertEquals("Your fleet size total must not exceed 6. You must have at\n"
        + "least 1 of every type. Try again.\n"
        + "Fleet: ", outputStreamCaptor.toString());
  }

  @Test
  void promptCoords() {
    view.promptCoords(6);
    assertEquals("Please enter 6 coordinates to attack:\n", outputStreamCaptor.toString());
  }

  @Test
  void invalidCoords() {
    view.invalidCoords();
    assertEquals("I'm sorry. You have entered an invalid set of coordinates.\n"
        + "Please try again:\n", outputStreamCaptor.toString());
  }

  @Test
  void gameOver() {
    view.gameOver(GameResult.WIN, "You win!");
    assertEquals("The game resulted in a Win\nYou win!\n", outputStreamCaptor.toString());
  }
}