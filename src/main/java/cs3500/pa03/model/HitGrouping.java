package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.Orientation;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grouping of hits in a game board.
 */
public record HitGrouping(int length, Orientation orientation, List<Coord> coords) {

  /**
   * Constructs a new HitGrouping.
   *
   * @param length      The length of the hit grouping.
   * @param orientation The orientation of the hit grouping.
   * @param coords      The coordinates of the hit grouping.
   */
  public HitGrouping(int length, Orientation orientation, List<Coord> coords) {
    this.length = length;
    this.orientation = orientation;
    this.coords = new ArrayList<>(coords);  // Creating new list for immutability
  }

  /**
   * Returns a new list of the coordinates, preserving immutability.
   *
   * @return a new list containing the coordinates of the hit grouping.
   */
  @Override
  public List<Coord> coords() {
    return new ArrayList<>(this.coords);  // Returning new list for immutability
  }

  /**
   * Returns a list of coordinates for unchecked sides of the hit grouping.
   *
   * @param enemyBoard The enemy's game board.
   * @return a list of coordinates for unchecked sides.
   */
  public List<Coord> getUncheckedSides(Coord[][] enemyBoard) {
    List<Coord> uncheckedSides = new ArrayList<>();
    Coord topLeft = coords.get(0);
    Coord bottomRight = coords.get(0);

    for (Coord c : coords) {
      if (topLeft.getX() > c.getX() || topLeft.getY() > c.getY()) {
        topLeft = c;
      }
      if (bottomRight.getX() < c.getX() || bottomRight.getY() < c.getY()) {
        bottomRight = c;
      }
    }

    if (this.orientation.equals(Orientation.VERTICAL)) {
      addIfEmptyAndWithinBounds(uncheckedSides, enemyBoard, topLeft.getX(), topLeft.getY() - 1);
      addIfEmptyAndWithinBounds(uncheckedSides, enemyBoard, bottomRight.getX(),
          bottomRight.getY() + 1);
    } else {
      addIfEmptyAndWithinBounds(uncheckedSides, enemyBoard, topLeft.getX() - 1, topLeft.getY());
      addIfEmptyAndWithinBounds(uncheckedSides, enemyBoard, bottomRight.getX() + 1,
          bottomRight.getY());
    }
    return uncheckedSides;
  }

  /**
   * Adds a coordinate to the unchecked sides if the coordinate is within the game board bounds and
   * its type is empty.
   *
   * @param uncheckedSides The list of unchecked sides to add the coordinate to.
   * @param enemyBoard     The enemy's game board.
   * @param x              The x coordinate.
   * @param y              The y coordinate.
   */
  private void addIfEmptyAndWithinBounds(List<Coord> uncheckedSides, Coord[][] enemyBoard, int x,
                                         int y) {
    if (x >= 0 && y >= 0 && x < enemyBoard.length && y < enemyBoard[0].length
        && enemyBoard[x][y].getType().equals(CoordType.EMPTY)) {
      uncheckedSides.add(new Coord(x, y));
    }
  }
}
