package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.ShipType;
import cs3500.pa03.singleton.Randomizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class implements any methods
 * that are the same between the computer
 * and the user type players.
 */
public class PlayerUtils {

  /**
   * Generic setup method between the two Player Classes
   *
   * @param specifications   HashMap Specifications for the fleet
   * @param myBoard          Coord[][] representing the player's board
   * @return                 List of Ships representing their fleet on the given board
   */
  public static List<Ship> setup(Map<ShipType, Integer> specifications, Coord[][] myBoard) {
    List<Ship> ships = new ArrayList<>();
    initializeBoard(myBoard);
    List<ShipType> types = ShipType.asList();

    for (ShipType t : types) {
      for (int i = 0; i < specifications.get(t); i++) {
        ships.add(new Ship(tryPlacement(t.getSize(), myBoard), t));
      }
    }

    return ships;
  }

  /**
   * Attempts to place this ship on the board.
   *
   * @return    List of Coords representing the ship's successful placement on the board
   */
  private static List<Coord> tryPlacement(int length, Coord[][] board) {
    int maxX = board.length - 1;
    int maxY = board[maxX].length - 1;
    Random r = Randomizer.getRandomizer().getRandom();

    List<Coord> placement = new ArrayList<>(Arrays.asList(new Coord(-1, -1)));

    while (!validPlacement(placement, board)) {
      placement = new ArrayList<>();
      int initialX = r.nextInt(maxX + 1);
      int initialY = r.nextInt(maxY + 1);
      int orientation = r.nextInt(2);
      //vertical
      if (orientation == 0) {
        for (int i = 0; i < length; i++) {
          placement.add(new Coord(initialX, initialY - i));
        }
        //horizontal
      } else {
        for (int i = 0; i < length; i++) {
          placement.add(new Coord(initialX + i, initialY));
        }
      }
    }

    //put the ships onto the board
    for (Coord c : placement) {
      c.setType(CoordType.SHIP);
      board[c.getX()][c.getY()] = c;
    }
    return placement;
  }

  /**
   * Determines whether or not the given list of Coordinates fits on the given board
   *
   * @param lc      List of Coordinates to check
   * @param board   Coord[][] board
   * @return        Boolean, whether or not the coords are validly placed on the board
   */
  private static boolean validPlacement(List<Coord> lc, Coord[][] board) {
    int maxX = board.length - 1;
    int maxY = board[maxX].length - 1;
    for (Coord c : lc) {
      //Ship Out of Bounds
      if (c.getX() < 0 || c.getY() < 0 || c.getX() > maxX || c.getY() > maxY) {
        return false;
      }
      //Ship overlaps existing ship
      if (board[c.getX()][c.getY()].getType().equals(CoordType.SHIP)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Instantiates a Coord[][] board with empty Coord objects;
   *
   * @param board   Coord[][] to instantiate
   */
  public static void initializeBoard(Coord[][] board) {
    for (int c = 0; c < board.length; c++) {
      for (int r = 0; r < board[c].length; r++) {
        board[c][r] = new Coord(c, r);
      }
    }
  }

  /**
   * Generic reportDamage method for both Player Classes.
   * This method reports damage on this player that called this method,
   * given shots from the opposing player.
   * Also, mutates the given board to reflect the fact that this board
   * has been fired upon.
   *
   * @param opponentShotsOnBoard    List of Coord, shots from the opponent
   * @param myBoard                 Coord[][] the board being shot onto
   * @param myShips                 List of Ships, ships being shot onto
   * @return                        List of Coords - successful shots on this board
   */
  public static List<Coord> reportDamage(
      List<Coord> opponentShotsOnBoard, Coord[][] myBoard, List<Ship> myShips) {
    List<Coord> hits = reportHits(opponentShotsOnBoard, myBoard);
    List<Coord> misses = reportMisses(opponentShotsOnBoard, myBoard);
    updateShips(opponentShotsOnBoard, myShips);

    //mutate given board
    for (Coord c : hits) {
      myBoard[c.getX()][c.getY()] = new Coord(c.getX(), c.getY(), CoordType.SHIPHIT);
    }
    for (Coord c : misses) {
      myBoard[c.getX()][c.getY()] = new Coord(c.getX(), c.getY(), CoordType.MISS);
    }
    return hits;
  }

  /**
   * Returns the successful hits of the opponent onto the given board as a
   * List of Coords.
   *
   * @param opponentShotsOnBoard    List of Coords, shots from the opponent
   * @param myBoard                 Coord[][] Board being fired upon
   * @return                        List of Coords - successful shots on this board
   */
  private static List<Coord> reportHits(List<Coord> opponentShotsOnBoard, Coord[][] myBoard) {
    List<Coord> successfulHits = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      if (myBoard[c.getX()][c.getY()].getType().equals(CoordType.SHIP)
          || myBoard[c.getX()][c.getY()].getType().equals(CoordType.SHIPHIT)) {
        successfulHits.add(c);
      }
    }
    return successfulHits;
  }

  /**
   * Returns the misses of the opponent onto the given board as a
   * List of Coords.
   *
   * @param opponentShotsOnBoard    List of Coords, shots from the opponent
   * @param myBoard                 Coord[][] Board being fired upon
   * @return                        List of Coords - misses from the opponent
   */
  private static List<Coord> reportMisses(List<Coord> opponentShotsOnBoard, Coord[][] myBoard) {
    List<Coord> unsuccessfulHits = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      if (!myBoard[c.getX()][c.getY()].getType().equals(CoordType.SHIP)) {
        unsuccessfulHits.add(c);
      }
    }
    return unsuccessfulHits;
  }

  /**
   * Updates the given list of ships so that they reflect being fired upon.
   *
   * @param opponentShotsOnBoard  List of Coords, shots from the opponent
   * @param myShips               List of Ships, the players ships being fired upon
   */
  private static void updateShips(List<Coord> opponentShotsOnBoard, List<Ship> myShips) {
    for (Ship s : myShips) {
      for (Coord c : opponentShotsOnBoard) {
        for (Coord sc : s.getShipCoords()) {
          if (sc.equals(c)) {
            sc.setType(CoordType.SHIPHIT);
          }
        }
      }
    }
  }
}
