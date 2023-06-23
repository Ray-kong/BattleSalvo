package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.CoordType;

/**
 * Represents a 2D Coordinate for this game.
 * Carries an X, Y and a type of Coordinate.
 */
public class Coord {
  private int xpos;
  private int ypos;
  private CoordType type;

  /**
   * Coord constructor - 2 Params.
   * Default type set to EMPTY
   *
   * @param x   x position of this Coord
   * @param y   y position of this Coord
   */
  public Coord(int x, int y) {
    this.xpos = x;
    this.ypos = y;
    this.type = CoordType.EMPTY;
  }

  /**
   * Coord constructor - 3 Params.
   *
   * @param x   x position of this Coord
   * @param y   y position of this Coord
   * @param t   CoordType of this Coord
   */
  public Coord(int x, int y, CoordType t) {
    this.xpos = x;
    this.ypos = y;
    this.type = t;
  }

  /**
   * Gets the X position of this coordinate.
   *
   * @return int x position
   */
  public int getX() {
    return this.xpos;
  }

  /**
   * Gets the Y position of this coordinate.
   *
   * @return int y position
   */
  public int getY() {
    return this.ypos;
  }

  /**
   * Gets the type of this coordinate.
   *
   * @return CoordType of this Coord
   */
  public CoordType getType() {
    return this.type;
  }

  /**
   * Sets the type of this coordinate.
   *
   * @param t CoordType to set this Coord to.
   */
  public void setType(CoordType t) {
    this.type = t;
  }

  /**
   * Coord Equality
   * A Coord is equal to another Coord if and only if
   * their X and Y positions are the same. CoordType is
   * negligible in terms of this definition of equality.
   *
   * @param o object to compare equality to
   * @return boolean if they are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Coord) {
      return ((Coord) o).xpos == this.xpos && ((Coord) o).ypos == this.ypos;
    }
    return false;
  }

  public String toString() {
    return "[" + xpos + ", " + ypos + "]\n";
  }
}
