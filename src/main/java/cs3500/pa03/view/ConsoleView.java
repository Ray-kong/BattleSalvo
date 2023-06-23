package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumerations.GameResult;

/**
 * The responsibility of this class is to display the view
 * to the Console. Implementing ViewInterface.
 */
public class ConsoleView implements ViewInterface {

  /**
   * Displays the given Board - Coord[][].
   *
   * @param board   Coord[][] to display
   */
  @Override
  public void displayBoard(Coord[][] board) {
    for (int r = 0; r < board[0].length; r++) {
      for (int c = 0; c < board.length; c++) {
        if (r == 0 && c == 0) {
          System.out.print("┌─────┬");
        } else if (r == 0 && c == board.length - 1) {
          System.out.print("─────┐");
        } else if (r == 0) {
          System.out.print("─────┬");
        } else if (c == board.length - 1) {
          System.out.print("─────┤");
        } else if (c == 0) {
          System.out.print("├─────┼");
        } else {
          System.out.print("─────┼");
        }
      }
      System.out.print("\n");
      for (int c = 0; c < board.length; c++) {
        if (c == 0) {
          System.out.print("│");
        }
        displayCoord(board[c][r]);
      }
      System.out.print("\n");
    }
    for (int c = 0; c < board.length; c++) {
      if (c == 0) {
        System.out.print("└─────┴");
      } else if (c == board.length - 1) {
        System.out.print("─────┘\n");
      } else {
        System.out.print("─────┴");
      }
    }
  }

  /**
   * Displays a given Coord
   *
   * @param c   Coord to display
   */
  @Override
  public void displayCoord(Coord c) {
    switch (c.getType()) {
      case HIT:
        System.out.print("  ╳  │");
        break;
      case MISS:
        System.out.print("  ◌  │");
        break;
      case EMPTY:
        System.out.print("     │");
        break;
      case SHIP:
        System.out.print(" ▐█▌ │");
        break;
      case SHIPHIT:
        System.out.print(" ▐╳▌ │");
        break;
      default: break;
    }
  }

  /**
   *  Displays the welcome message.
   */
  @Override
  public void welcome() {
    System.out.println("\n"
        + "████████████████████████████████████████████████████████████████████\n"
        + "█▄ ▄ ▀██▀  ██ ▄ ▄ █ ▄ ▄ █▄ ▄███▄ ▄▄ ███ ▄▄▄▄██▀  ██▄ ▄███▄ █ ▄█ ▄▄ █\n"
        + "██ ▄ ▀██ ▀ ████ █████ ████ ██▀██ ▄█▀███▄▄▄▄ ██ ▀ ███ ██▀██▄ ▄██ ██ █\n"
        + "█▄▄▄▄██▄▄█▄▄██▄▄▄███▄▄▄██▄▄▄▄▄█▄▄▄▄▄███▄▄▄▄▄█▄▄█▄▄█▄▄▄▄▄███▄███▄▄▄▄█");
    System.out.println("Directions: Destroy the computer in OOD Battleship.\n");
    System.out.println("Legend:\n"
        + " ╳  = You Hit an Enemy Ship\n"
        + " ◌  = Miss\n"
        + "▐█▌ = Your Ship\n"
        + "▐╳▌ = The Enemy Hit Your Ship\n"
        + "    = Empty\n");
  }

  /**
   *  Prompts the user for the board size
   */
  @Override
  public void promptBoardSize() {
    System.out.println("Please enter dimensions you wish the board to be below [width height].");
    System.out.print("Board Size: ");
  }

  /**
   * When an invalid board size is given, this message is prompted.
   *
   * @param min   int minimum board size
   * @param max   int maximum board size
   */
  @Override
  public void invalidBoardSize(int min, int max) {
    System.out.println("I'm sorry, those are invalid dimensions. Both must be between "
        + min + " and " + max + ". Please retry below.");
    System.out.print("Board Size: ");
  }

  /**
   *  Prompts the user to enter a fleet of ships
   *
   * @param max int ships to add
   */
  @Override
  public void promptShips(int max) {
    System.out.println("Please enter the number of each type of ship in the order \n"
        + "[Carrier Battleship Destroyer Submarine]. Your fleet size total must \n"
        + "not exceed " + max + ".");
    System.out.print("Fleet: ");
  }

  /**
   * When an invalid fleet is given, the message is prompted
   *
   * @param max   int max number of ships allowed
   */
  @Override
  public void invalidShips(int max) {
    System.out.println("Your fleet size total must not exceed " + max + ". You must have at\n"
        + "least 1 of every type. Try again.");
    System.out.print("Fleet: ");
  }

  /**
   *  Prompts the user for coordinates to attack
   *
   * @param num   number of shots to take
   */
  @Override
  public void promptCoords(int num) {
    System.out.println("Please enter " + num + " coordinates to attack:");
  }

  /**
   *  When given invalid coords, this message is prompted
   */
  @Override
  public void invalidCoords() {
    System.out.println("I'm sorry. You have entered an invalid set of coordinates.\n"
        + "Please try again:");
  }

  /**
   *  Displays a game over screen with the result
   *
   * @param result  GameResult to display
   * @param reason  Reason for game ending as a String
   */
  @Override
  public void gameOver(GameResult result, String reason) {
    System.out.println("The game resulted in a " + GameResult.toString(result));
    System.out.println(reason);
  }
}
