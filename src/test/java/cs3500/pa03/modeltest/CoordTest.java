package cs3500.pa03.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumerations.CoordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {
  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;
  Coord c5;
  Coord c6;

  @BeforeEach
  void setUp() {
    c1 = new Coord(0, 0);
    c2 = new Coord(1, 1);
    c3 = new Coord(1, 1, CoordType.SHIPHIT);
    c4 = new Coord(1, 1, CoordType.MISS);
    c5 = new Coord(1, 2, CoordType.HIT);
    c6 = new Coord(2, 1, CoordType.HIT);
  }

  @Test
  void getX() {
    assertEquals(0, c1.getX());
    assertEquals(1, c2.getX());
    assertEquals(1, c5.getX());
  }

  @Test
  void getY() {
    assertEquals(0, c1.getY());
    assertEquals(1, c2.getY());
    assertEquals(2, c5.getY());
  }

  @Test
  void getType() {
    assertEquals(CoordType.EMPTY, c1.getType());
    assertEquals(CoordType.SHIPHIT, c3.getType());
    assertEquals(CoordType.HIT, c5.getType());
  }

  @Test
  void setType() {
    c1.setType(CoordType.HIT);
    assertEquals(CoordType.HIT, c1.getType());

    c2.setType(CoordType.MISS);
    assertEquals(CoordType.MISS, c2.getType());

    c3.setType(CoordType.EMPTY);
    assertEquals(CoordType.EMPTY, c3.getType());
  }

  @Test
  void testEquals() {
    assertFalse(c1.equals(c2));
    assertTrue(c2.equals(c3));
    assertTrue(c3.equals(c4));
    assertFalse(c4.equals(c5));
    assertFalse(c5.equals(c6));
    assertFalse(c1.equals("Not a Coord"));
  }
}