package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.ShipType;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Ship in the game.
 * A ship carries information like which coordinates on
 * a board it occupies, whether it is sunk, and what
 * type of ship it is.
 */
public class Ship {
  List<Coord> shipCoords;
  boolean sunk;
  ShipType shipType;

  /**
   * Ship constructor
   *
   * @param lc   List of Coords this ship occupies
   * @param st   the type of ship this ship is
   */
  public Ship(List<Coord> lc, ShipType st) {
    this.shipCoords = lc;
    this.sunk = false;
    this.shipType = st;
  }

  /**
   * Method that takes in a list of hits
   * and returns which ones hit this ship.
   * Also updates this ship's coord state.
   *
   * @param potentialHits potential hits on this ship
   * @return              List of Coords that hit this ship
   */
  public List<Coord> gotHit(List<Coord> potentialHits) {
    List<Coord> outputList = new ArrayList<>();
    for (Coord hit : potentialHits) {
      for (Coord ship : this.shipCoords) {
        if (hit.equals(ship)) {
          ship.setType(CoordType.SHIPHIT);
          outputList.add(hit);
        }
      }
    }
    return outputList;
  }

  /**
   * Is this ship sunk? To be sunk, all of
   * the Coords the ship covers must be hits.
   *
   * @return boolean - if the ship is sunk
   */
  public boolean isSunk() {
    boolean b = true;
    for (Coord c : this.shipCoords) {
      b = b && c.getType().equals(CoordType.SHIPHIT);
    }
    return b;
  }

  /**
   * Gets this ship type
   *
   * @return this ShipType of this ship
   */
  public ShipType getShipType() {
    return this.shipType;
  }

  /**
   * Gets the Coords this ship occupies as a list
   *
   * @return    List of Coords this ship occupies
   */
  public List<Coord> getShipCoords() {
    return this.shipCoords;
  }
}