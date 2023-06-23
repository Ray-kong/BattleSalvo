package cs3500.pa03.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.PlayerUtils;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.ShipType;
import cs3500.pa03.singleton.Randomizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerUtilsTest {
  Map<ShipType, Integer> specs;
  Coord[][] board1;

  List<Ship> ships1;
  Ship ship1;
  Ship ship2;
  Ship ship3;
  Ship ship4;

  List<Coord> opponentShotsOnBoard;

  @BeforeEach
  void setUp() {
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    //updates the singleton to use a random seed for testing
    Randomizer.getRandomizer().updateSeed(100);

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
  }

  @Test
  void setup() {
    List<Ship> ships = PlayerUtils.setup(specs, board1);

    for (int i = 0; i < ships.size(); i++) {
      for (int j = 0; j < ships.get(i).getShipCoords().size(); j++) {
        assertEquals(ships1.get(i).getShipCoords().get(j), ships.get(i).getShipCoords().get(j));
      }
    }
  }

  @Test
  void reportDamage() {
    //ships1 goes onto board
    for (Ship s : ships1) {
      for (Coord c : s.getShipCoords()) {
        board1[c.getX()][c.getY()].setType(CoordType.SHIP);
      }
    }
    List<Coord> damage = PlayerUtils.reportDamage(opponentShotsOnBoard, board1, ships1);
    List<Coord> damage2 = PlayerUtils.reportDamage(opponentShotsOnBoard, board1, ships1);

    assertEquals(new Coord(5, 5), damage.get(0));
    assertEquals(new Coord(5, 5), damage2.get(0));
  }
}