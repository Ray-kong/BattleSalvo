package cs3500.pa03.modeltest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa03.model.ComputerPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.PlayerUtils;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.GameResult;
import cs3500.pa03.model.enumerations.ShipType;
import cs3500.pa03.singleton.Randomizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComputerPlayerTest {
  Map<ShipType, Integer> specs;
  Coord[][] board1;
  List<Ship> ships1;
  Ship ship1;
  Ship ship2;
  Ship ship3;
  Ship ship4;

  List<Coord> opponentShotsOnBoard;
  List<Coord> shotsTaken1;
  List<Coord> shotsTaken2;
  ComputerPlayer player;

  @BeforeEach
  void setUp() {
    //updates the singleton to use a random seed for testing
    Randomizer.getRandomizer().updateSeed(100);

    player = new ComputerPlayer();

    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    //updates the singleton to use a random seed for testing
    Randomizer.getRandomizer().updateSeed(100);

    board1 = new Coord[6][6];
    PlayerUtils.initializeBoard(board1);

    shotsTaken1 = new ArrayList<>(Arrays.asList(
        new Coord(5, 5),
        new Coord(1, 2),
        new Coord(3, 5),
        new Coord(2, 4)
    ));
    shotsTaken2 = new ArrayList<>(Arrays.asList(
        new Coord(0, 0),
        new Coord(5, 4),
        new Coord(1, 5),
        new Coord(1, 4)
    ));

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

  @AfterEach
  void tearDown() {
    Randomizer.getRandomizer().updateSeed();
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
    assertDoesNotThrow(() -> player.endGame(GameResult.DRAW,
        "The Salvo ended in mutual destruction."));
  }
}