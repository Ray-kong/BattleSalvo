package cs3500.pa03.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.GameState;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.enumerations.ShipType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStateTest {

  GameState state;
  Map<ShipType, Integer> specs;

  @BeforeEach
  void setUp() {
    state = new GameState();
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 2);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 2);
  }

  @Test
  void getP1() {
    Player p = state.getP1();
    assertEquals(p, state.getP1());
  }

  @Test
  void getP2() {
    Player p = state.getP2();
    assertEquals(p, state.getP2());
  }

  @Test
  void getSpecifications() {
    state.setSpecifications(specs);
    assertEquals(specs, state.getSpecifications());
  }

  @Test
  void setSpecifications() {
    state.setSpecifications(specs);
    assertEquals(specs, state.getSpecifications());
  }

  @Test
  void getWidth() {
    state.setWidth(6);
    assertEquals(6, state.getWidth());

    state.setWidth(7);
    assertEquals(7, state.getWidth());

    state.setWidth(15);
    assertEquals(15, state.getWidth());
  }

  @Test
  void setWidth() {
    state.setWidth(6);
    assertEquals(6, state.getWidth());

    state.setWidth(7);
    assertEquals(7, state.getWidth());

    state.setWidth(15);
    assertEquals(15, state.getWidth());
  }

  @Test
  void getHeight() {
    state.setHeight(6);
    assertEquals(6, state.getHeight());

    state.setHeight(7);
    assertEquals(7, state.getHeight());

    state.setHeight(15);
    assertEquals(15, state.getHeight());
  }

  @Test
  void setHeight() {
    state.setHeight(6);
    assertEquals(6, state.getHeight());

    state.setHeight(7);
    assertEquals(7, state.getHeight());

    state.setHeight(15);
    assertEquals(15, state.getHeight());
  }
}