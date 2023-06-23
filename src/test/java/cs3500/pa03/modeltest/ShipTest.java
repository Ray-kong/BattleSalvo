package cs3500.pa03.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumerations.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  Ship s1;
  Ship s2;
  Ship s3;
  Ship s4;

  List<Coord> s1Coords;
  List<Coord> s2Coords;
  List<Coord> s3Coords;
  List<Coord> s4Coords;

  @BeforeEach
  void setUp() {
    s1Coords = new ArrayList<>(Arrays.asList(
        new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2),
        new Coord(0, 3),
        new Coord(0, 4),
        new Coord(0, 5)
    ));
    s1 = new Ship(s1Coords, ShipType.CARRIER);

    s2Coords = new ArrayList<>(Arrays.asList(
        new Coord(1, 0),
        new Coord(1, 1),
        new Coord(1, 2),
        new Coord(1, 3),
        new Coord(1, 4)
    ));
    s2 = new Ship(s2Coords, ShipType.BATTLESHIP);

    s3Coords = new ArrayList<>(Arrays.asList(
        new Coord(2, 0),
        new Coord(2, 1),
        new Coord(2, 2),
        new Coord(2, 3)
    ));
    s3 = new Ship(s3Coords, ShipType.DESTROYER);

    s4Coords = new ArrayList<>(Arrays.asList(
        new Coord(3, 0),
        new Coord(3, 1),
        new Coord(3, 2)
    ));
    s4 = new Ship(s4Coords, ShipType.SUBMARINE);
  }

  @Test
  void gotHit() {
    assertEquals(new ArrayList<>(), s1.gotHit(s2Coords));
    assertEquals(new ArrayList<>(), s2.gotHit(s3Coords));
    assertEquals(s1Coords, s1.gotHit(s1Coords));
    assertEquals(s4Coords, s4.gotHit(s4Coords));
  }

  @Test
  void isSunk() {
    s1.gotHit(s1Coords);
    assertTrue(s1.isSunk());

    s3.gotHit(s3Coords);
    assertTrue(s3.isSunk());

    assertFalse(s2.isSunk());
    assertFalse(s4.isSunk());
  }

  @Test
  void getShipType() {
    assertEquals(ShipType.CARRIER, s1.getShipType());
    assertEquals(ShipType.BATTLESHIP, s2.getShipType());
    assertEquals(ShipType.DESTROYER, s3.getShipType());
    assertEquals(ShipType.SUBMARINE, s4.getShipType());
  }

  @Test
  void getShipCoords() {
    assertEquals(s1Coords, s1.getShipCoords());
    assertEquals(s2Coords, s2.getShipCoords());
    assertEquals(s3Coords, s3.getShipCoords());
    assertEquals(s4Coords, s4.getShipCoords());
  }
}