package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * The responsibility of this class is to hold
 * the state of the game as it progresses.
 */
public class GameState {
  private UserPlayer p1;
  private ComputerPlayer p2;

  private Map<ShipType, Integer> specifications;
  private int width;
  private int height;

  /**
   * Constructor for a GameState
   * Initializes two players, and what the fleet specs are.
   */
  public GameState() {
    this.p1 = new UserPlayer();
    this.p2 = new ComputerPlayer();
    this.specifications = new HashMap<>();
  }

  /**
   * Returns the Player1 object
   *
   * @return  UserPlayer object
   */
  public UserPlayer getP1() {
    return p1;
  }

  /**
   * Returns the Player2 object
   *
   * @return  ComputerPlayer object
   */
  public ComputerPlayer getP2() {
    return p2;
  }

  /**
   * Gets the fleet specifications for this GameState
   *
   * @return HashMap of the fleet specifications
   */
  public Map<ShipType, Integer> getSpecifications() {
    return this.specifications;
  }

  /**
   * Sets the fleet specifications to the given HashMap
   *
   * @param specs   new fleet specifications
   */
  public void setSpecifications(Map<ShipType, Integer> specs) {
    this.specifications = specs;
  }

  /**
   * Gets the width of this game board
   *
   * @return int width of board
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Sets the width of this game board
   *
   * @param w  int width to set the game board to
   */
  public void setWidth(int w) {
    this.width = w;
  }

  /**
   * Gets the height of this game board
   *
   * @return int height of board
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Sets the height of this game board
   *
   * @param h  int height to set the game board to
   */
  public void setHeight(int h) {
    this.height = h;
  }
}