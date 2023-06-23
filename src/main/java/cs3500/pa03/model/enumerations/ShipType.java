package cs3500.pa03.model.enumerations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumeration Type to represent the types that a ship can be.
 */
public enum ShipType {

  /**
   * Carrier, 6 length
   */
  CARRIER(6),

  /**
   * Battleship, 5 length
   */
  BATTLESHIP(5),

  /**
   * Destroyer, 4 length
   */
  DESTROYER(4),

  /**
   * Submarine, 3 length
   */
  SUBMARINE(3);

  private int size;

  /**
   * Returns the size of this type of ship.
   *
   * @return int size of this ship type
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns a List of ShipTypes of all the types that exist
   *
   * @return  List of ShipTypes
   */
  public static List<ShipType> asList() {
    return new ArrayList<>(Arrays.asList(
        ShipType.CARRIER,
        ShipType.BATTLESHIP,
        ShipType.DESTROYER,
        ShipType.SUBMARINE));
  }

  /**
   * Private constructor for a ShipType
   *
   * @param n   size of the ShipType
   */
  private ShipType(int n) {
    this.size = n;
  }
}