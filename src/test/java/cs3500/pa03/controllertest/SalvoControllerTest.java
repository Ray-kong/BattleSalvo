package cs3500.pa03.controllertest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.controller.SalvoController;
import cs3500.pa03.model.Coord;
import cs3500.pa03.singleton.InputScanner;
import cs3500.pa03.singleton.Randomizer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalvoControllerTest {
  private static final InputStream standardIn = System.in;
  ByteArrayInputStream inputStreamCaptor1;

  List<Coord> shotsTaken;

  @BeforeEach
  void setUp() {
    Randomizer.getRandomizer().updateSeed(100);
    inputStreamCaptor1 = new ByteArrayInputStream(("-15 15\n"
        + "15 15\n"
        + "0 15\n"
        + "15 0\n"
        + "-1 0\n"
        + "0 -1\n"
        + "0 0\n"
        + "1 1\n"
        + "2 2\n"
        + "3 3\n"
        + "4 4\n").getBytes());
    shotsTaken = new ArrayList<>(Arrays.asList(
        new Coord(0, 0),
        new Coord(1, 1),
        new Coord(2, 2),
        new Coord(3, 3)
    ));
  }

  @AfterAll
  static void tearDown() {
    Randomizer.getRandomizer().updateSeed();
    InputScanner.getInputScanner().changeInputStream(standardIn);
  }

  @Test
  void getUserAttack() {
    InputScanner.getInputScanner().changeInputStream(inputStreamCaptor1);
    List<Coord> shots = SalvoController.getUserAttack(4, 6, 6);
    for (int i = 0; i < shots.size(); i++) {
      assertTrue(shotsTaken.get(i).equals(shots.get(i)));
    }
  }
}