package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumerations.GameResult;

/**
 * Interface for the view. Any class that implements
 * this interface should be able to make a view for the program.
 */
public interface ViewInterface {

  /**
   * Displays the given Board - Coord[][].
   *
   * @param board   Coord[][] to display
   */
  void displayBoard(Coord[][] board);

  /**
   * Displays a given Coord
   *
   * @param c   Coord to display
   */
  void displayCoord(Coord c);

  /**
   *  Displays the welcome message.
   */
  void welcome();

  /**
   *  Prompts the user for the board size
   */
  void promptBoardSize();

  /**
   * When an invalid board size is given, this message is prompted.
   *
   * @param min   int minimum board size
   * @param max   int maximum board size
   */
  void invalidBoardSize(int min, int max);

  /**
   *  Prompts the user to enter a fleet of ships
   *
   * @param max int max number of ships allowed
   */
  void promptShips(int max);

  /**
   * When an invalid fleet is given, the message is prompted
   *
   * @param max   int max number of ships allowed
   */
  void invalidShips(int max);

  /**
   *  Prompts the user for coordinates to attack
   *
   * @param num   number of shots to take
   */
  void promptCoords(int num);

  /**
   *  When given invalid coords, this message is prompted
   */
  void invalidCoords();

  /**
   *  Displays a game over screen with the result
   *
   * @param result  GameResult to display
   * @param reason  Reason for game ending as a String
   */
  void gameOver(GameResult result, String reason);
}