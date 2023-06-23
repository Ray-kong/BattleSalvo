package cs3500.pa03.modeltest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.PlayerUtils;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.UserPlayer;
import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.GameResult;
import cs3500.pa03.model.enumerations.ShipType;
import cs3500.pa03.singleton.InputScanner;
import cs3500.pa03.singleton.Randomizer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPlayerTest {
  Map<ShipType, Integer> specs;
  Coord[][] board1;

  List<Ship> ships1;
  Ship ship1;
  Ship ship2;
  Ship ship3;
  Ship ship4;

  List<Coord> opponentShotsOnBoard;
  List<Coord> shotsTaken;
  private static final InputStream standardIn = System.in;

  UserPlayer player;

  @BeforeEach
  void setUp() {
    //updates the singleton to use a random seed for testing
    Randomizer.getRandomizer().updateSeed(100);
    ByteArrayInputStream inputStreamCaptor = new ByteArrayInputStream(((
        "0 0\n1 0\n2 0\n3 0\n4 0\n5 0\n"
        + "0 1\n1 1\n2 1\n3 1\n4 1\n5 1\n"
        + "0 2\n1 2\n2 2\n3 2\n4 2\n5 2\n"
        + "0 3\n1 3\n2 3\n3 3\n4 3\n5 3\n"
        + "0 4\n1 4\n2 4\n3 4\n4 4\n5 4\n"
        + "0 5\n1 5\n2 5\n3 5\n4 5\n5 5\n").getBytes()));
    InputScanner.getInputScanner().changeInputStream(inputStreamCaptor);

    shotsTaken = new ArrayList<>(Arrays.asList(
        new Coord(0, 0),
        new Coord(1, 0),
        new Coord(2, 0),
        new Coord(3, 0),
        new Coord(4, 0),
        new Coord(5, 0)
    ));

    player = new UserPlayer();
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);

    board1 = new Coord[6][6];
    PlayerUtils.initializeBoard(board1);

    ship1 = new Ship(new ArrayList<>(Arrays.asList(
        new Coord(5, 5),
        new Coord(5, 4),
        new Coord(5, 3),
        new Coord(5, 2),
        new Coord(5, 1),
        new Coord(5, 0)
    )), ShipType.CARRIER);

    ship2 = new Ship(new ArrayList<>(Arrays.asList(
        new Coord(0, 4),
        new Coord(0, 3),
        new Coord(0, 2),
        new Coord(0, 1),
        new Coord(0, 0)
    )), ShipType.BATTLESHIP);

    ship3 = new Ship(new ArrayList<>(Arrays.asList(
        new Coord(3, 4),
        new Coord(3, 3),
        new Coord(3, 2),
        new Coord(3, 1)
    )), ShipType.DESTROYER);

    ship4 = new Ship(new ArrayList<>(Arrays.asList(
        new Coord(1, 5),
        new Coord(2, 5),
        new Coord(3, 5)
    )), ShipType.SUBMARINE);

    ships1 = new ArrayList<>(Arrays.asList(ship1, ship2, ship3, ship4));

    opponentShotsOnBoard = new ArrayList<>(Arrays.asList(
        new Coord(4, 4),
        new Coord(5, 5)
    ));
  }

  @AfterAll
  static void tearDown() {
    //updates the singleton to go back to random with no set seed
    Randomizer.getRandomizer().updateSeed();
    InputScanner.getInputScanner().changeInputStream(standardIn);
  }

  @Test
  void name() {
    assertNull(player.name());
  }

  @Test
  void setup() {
    List<Ship> ships = player.setup(6, 6, specs);

    for (int i = 0; i < ships.size(); i++) {
      for (int j = 0; j < ships.get(i).getShipCoords().size(); j++) {
        assertEquals(ships1.get(i).getShipCoords().get(j), ships.get(i).getShipCoords().get(j));
      }
    }


  }

  @Test
  void takeShots() {
    player.setup(6, 6, specs);
    List<Coord> generated = player.takeShots();
    for (int i = 0; i < 4; i++) {
      assertTrue(shotsTaken.get(i).equals(generated.get(i)));
    }
  }

  @Test
  void reportDamage() {
    player.setup(6, 6, specs);
    for (Ship s : ships1) {
      for (Coord c : s.getShipCoords()) {
        board1[c.getX()][c.getY()].setType(CoordType.SHIP);
      }
    }
    List<Coord> damage = player.reportDamage(opponentShotsOnBoard);
    List<Coord> damage2 = player.reportDamage(opponentShotsOnBoard);

    assertEquals(new Coord(5, 5), damage.get(0));
    assertEquals(new Coord(5, 5), damage2.get(0));
  }

  @Test
  void successfulHits() {
    player.setup(6, 6, specs);
    player.successfulHits(opponentShotsOnBoard);
    assertEquals(CoordType.HIT, player.getEnemyBoard()[4][4].getType());
    assertEquals(CoordType.HIT, player.getEnemyBoard()[5][5].getType());

    assertEquals(CoordType.EMPTY, player.getEnemyBoard()[2][2].getType());
    assertEquals(CoordType.EMPTY, player.getEnemyBoard()[3][3].getType());
  }

  @Test
  void endGame() {
    assertDoesNotThrow(() -> player.endGame(GameResult.WIN, "You Win!"));
  }
}