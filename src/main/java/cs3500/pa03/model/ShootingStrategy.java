package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.Orientation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class defines a shooting strategy for a game of Battleships.
 */
public class ShootingStrategy {
  private Coord[][] enemyBoard;
  private List<Coord> previousShots;
  private List<Coord> shotsEveryThree;
  private List<Ship> myShips;
  private int curX;
  private int curY;

  /**
   * Constructs a new ShootingStrategy with the given boards, previous shots and ships.
   *
   * @param enemyBoard    2D array representing the enemy's board.
   * @param previousShots List of all previous shots.
   * @param myShips       List of all player's ships.
   */
  public ShootingStrategy(
      Coord[][] enemyBoard, List<Coord> previousShots, List<Ship> myShips) {
    this.enemyBoard = enemyBoard;
    this.previousShots = previousShots;
    this.shotsEveryThree = new ArrayList<>();
    this.myShips = myShips;

    //shotsEveryThree
    int startX = curX;
    while (curX < enemyBoard.length && curY < enemyBoard[0].length) {
      shotsEveryThree.add(new Coord(curX, curY));

      // Check if curX + 3 is within bounds before incrementing
      if (curX + 3 < enemyBoard.length) {
        curX += 3;
      } else {
        // Move to next row, but ensure startX is within bounds
        startX = (startX + 1) % 3;
        curX = startX;
        curY++;
      }
    }
  }

  /**
   * Generates a list of shot coordinates.
   *
   * @return A list of shot coordinates.
   */
  public List<Coord> generateShots() {
    List<Coord> isolatedHits = getIsolatedHits();
    List<HitGrouping> allGroups =
        getGroupings(Orientation.VERTICAL, enemyBoard.length, enemyBoard[0].length);
    List<HitGrouping> hzGroups =
        getGroupings(Orientation.HORIZONTAL, enemyBoard.length, enemyBoard[0].length);
    allGroups.addAll(hzGroups);

    List<Coord> surroundAll = generateAllPossibleShots(isolatedHits, allGroups);

    List<List<Coord>> categorizedEnemyBoard = categorizeEnemyBoard();
    List<Coord> emptySpaces = categorizedEnemyBoard.get(0);
    List<Coord> hits = categorizedEnemyBoard.get(1);

    Collections.shuffle(emptySpaces);
    List<Coord> availableAdjacents = surroundAllCoords(hits);
    surroundAll.addAll(availableAdjacents);
    surroundAll = filterOutPreviousAndRepeatedShots(surroundAll);

    int totalShots = calculateTotalShots();
    if (emptySpaces.size() < totalShots) {
      return emptySpaces;
    }

    surroundAll = fillUpSurroundAll(surroundAll, totalShots, emptySpaces);
    List<Coord> shots = prepareShots(surroundAll, totalShots);
    updateShotsAndEnemyBoard(shots);

    return shots;
  }

  /**
   * Calculates the total number of shots.
   *
   * @return The total number of shots.
   */
  private int calculateTotalShots() {
    int totalShots = 0;
    for (Ship s : myShips) {
      if (!s.isSunk()) {
        totalShots++;
      }
    }
    return totalShots;
  }

  /**
   * Generates all possible next shots given a list of isolated hits and groupings.
   *
   * @param isolatedHits A list of isolated hit coordinates.
   * @param allGroups    A list of hit groupings.
   * @return A list of possible next shot coordinates.
   */
  private List<Coord> generateAllPossibleShots(List<Coord> isolatedHits,
                                               List<HitGrouping> allGroups) {
    List<Coord> surroundAll = surroundAllCoords(isolatedHits);
    List<Coord> surroundGroups = surroundAllGroupings(allGroups);
    surroundAll.addAll(surroundGroups);
    return surroundAll;
  }

  /**
   * Categorizes all the coordinates on the enemy board into hits and empty spaces.
   *
   * @return A list containing two lists - the first list is of empty space coordinates
   *         and the second is of hit coordinates.
   */
  private List<List<Coord>> categorizeEnemyBoard() {
    List<Coord> emptySpaces = new ArrayList<>();
    List<Coord> hits = new ArrayList<>();
    for (Coord[] coords : enemyBoard) {
      for (int r = 0; r < coords.length; r++) {
        if (coords[r].getType().equals(CoordType.HIT)) {
          hits.add(coords[r]);
        } else if (coords[r].getType().equals(CoordType.EMPTY)) {
          emptySpaces.add(coords[r]);
        }
      }
    }
    return Arrays.asList(emptySpaces, hits);
  }

  /**
   * Filters out repeated and previous shots from a given list of coordinates.
   *
   * @param coords A list of coordinates.
   * @return A list of unique coordinates that were not previously shot.
   */
  private List<Coord> filterOutPreviousAndRepeatedShots(List<Coord> coords) {
    List<Coord> temp = new ArrayList<>();
    for (Coord c : coords) {
      if (!temp.contains(c) && !previousShots.contains(c)) {
        temp.add(c);
      }
    }
    return temp;
  }

  /**
   * Updates the enemy board and previous shots list with the given list of shots.
   *
   * @param shots A list of shots to be updated.
   */
  private void updateShotsAndEnemyBoard(List<Coord> shots) {
    previousShots.addAll(shots);
    for (Coord c : shots) {
      enemyBoard[c.getX()][c.getY()] = new Coord(c.getX(), c.getY(), CoordType.MISS);
    }
  }

  /**
   * Fills up the surroundAll array until it reaches the totalShots count.
   *
   * @param surroundAll A list of surrounding coordinates.
   * @param totalShots  The total number of shots.
   * @param emptySpaces A list of empty spaces on the board.
   * @return A list of coordinates filled up until it reaches the total shots count.
   */
  private List<Coord> fillUpSurroundAll(List<Coord> surroundAll, int totalShots,
                                        List<Coord> emptySpaces) {
    while (surroundAll.size() < totalShots) {
      List<Coord> pattern = getNextByThree(totalShots - surroundAll.size());
      surroundAll.addAll(pattern);

      if (shotsEveryThree.size() == 0 || pattern.size() == 0) {
        surroundAll.addAll(emptySpaces);
      }

      surroundAll = filterOutPreviousAndRepeatedShots(surroundAll);
    }
    return surroundAll;
  }

  /**
   * Prepares a list of shots from the surroundAll array.
   *
   * @param surroundAll A list of surrounding coordinates.
   * @param totalShots  The total number of shots.
   * @return A list of shot coordinates.
   */
  private List<Coord> prepareShots(List<Coord> surroundAll, int totalShots) {
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < totalShots; i++) {
      shots.add(surroundAll.get(i));
    }
    return shots;
  }

  /**
   * Calculates the next set of shots in a "three-step" pattern, to systematically cover the board.
   *
   * @param totalShots The total number of shots to generate.
   * @return A list of coordinates for the next shots.
   */
  private List<Coord> getNextByThree(int totalShots) {
    List<Coord> shots = new ArrayList<>();
    if (totalShots > shotsEveryThree.size()) {
      shotsEveryThree = new ArrayList<>();
      return shotsEveryThree;
    } else {
      for (int i = 0; i < totalShots; i++) {
        while (!shotsEveryThree.get(0).getType().equals(CoordType.EMPTY)) {
          shotsEveryThree.remove(0);
        }
        shots.add(shotsEveryThree.get(0));
        shotsEveryThree.remove(0);
      }
    }
    return shots;
  }

  /**
   * Generates a list of coordinates surrounding the given hits,
   * used to check the area around known hits for further targets.
   *
   * @param isolated List of hit coordinates to find surrounding area.
   * @return A list of coordinates surrounding the given hits.
   */
  private List<Coord> surroundAllCoords(List<Coord> isolated) {
    List<Coord> shots = new ArrayList<>();
    int boardWidth = enemyBoard.length;
    int boardHeight = enemyBoard[0].length;

    for (Coord c : isolated) {
      int x = c.getX();
      int y = c.getY();

      // check left
      if (x != 0 && enemyBoard[x - 1][y].getType().equals(CoordType.EMPTY)) {
        shots.add(enemyBoard[x - 1][y]);
      }
      // check right
      if (x != boardWidth - 1 && enemyBoard[x + 1][y].getType().equals(CoordType.EMPTY)) {
        shots.add(enemyBoard[x + 1][y]);
      }
      // check up
      if (y != 0 && enemyBoard[x][y - 1].getType().equals(CoordType.EMPTY)) {
        shots.add(enemyBoard[x][y - 1]);
      }
      // check down
      if (y != boardHeight - 1 && enemyBoard[x][y + 1].getType().equals(CoordType.EMPTY)) {
        shots.add(enemyBoard[x][y + 1]);
      }
    }
    return shots;
  }

  /**
   * Generates a list of coordinates surrounding the given groups of hits,
   * used to check the area around known hit groups for further targets.
   *
   * @param groups List of hit groupings to find surrounding area.
   * @return A list of coordinates surrounding the given hit groups.
   */
  private List<Coord> surroundAllGroupings(List<HitGrouping> groups) {
    List<Coord> shots = new ArrayList<>();
    for (HitGrouping hg : groups) {
      shots.addAll(hg.getUncheckedSides(enemyBoard));
    }
    return shots;
  }

  /**
   * Identifies isolated hit locations on the enemy board.
   *
   * @return A list of isolated hit locations.
   */
  private List<Coord> getIsolatedHits() {
    List<Coord> output = new ArrayList<>();
    for (int c = 0; c < enemyBoard.length; c++) {
      for (int r = 0; r < enemyBoard[c].length; r++) {
        if (enemyBoard[c][r].getType() == CoordType.HIT && checkIsolated(c, r)) {
          output.add(enemyBoard[c][r]);
        }
      }
    }
    return output;
  }

  /**
   * Checks if a given coordinate is isolated, i.e., it does not have any adjacent hits.
   *
   * @param c The column index of the coordinate.
   * @param r The row index of the coordinate.
   * @return True if the coordinate is isolated, false otherwise.
   */
  private boolean checkIsolated(int c, int r) {
    if (c > 0 && enemyBoard[c - 1][r].getType() == CoordType.HIT) {
      return false;
    }
    if (c < enemyBoard.length - 1 && enemyBoard[c + 1][r].getType() == CoordType.HIT) {
      return false;
    }
    if (r > 0 && enemyBoard[c][r - 1].getType() == CoordType.HIT) {
      return false;
    }
    return r >= enemyBoard[c].length - 1 || enemyBoard[c][r + 1].getType() != CoordType.HIT;
  }

  /**
   * Identifies and returns all groupings of hits based on a given orientation.
   *
   * @param orientation The orientation of the groupings to identify.
   * @param width       The width of the enemy board.
   * @param height      The height of the enemy board.
   * @return A list of all identified hit groupings in the given orientation.
   */
  private List<HitGrouping> getGroupings(Orientation orientation, int width, int height) {
    List<HitGrouping> allGroupings = new ArrayList<>();

    // determine if the iteration is vertical or horizontal.
    boolean isVertical = (orientation == Orientation.VERTICAL);

    int firstBound = isVertical ? width : height;
    int secondBound = isVertical ? height : width;

    for (int i = 0; i < firstBound; i++) {
      List<Coord> tempGroup = new ArrayList<>();

      for (int j = 0; j < secondBound; j++) {
        int firstIndex = isVertical ? i : j;
        int secondIndex = isVertical ? j : i;

        Coord currentCoord = enemyBoard[firstIndex][secondIndex];

        // if current coordinate is not a hit and we have a group, add it to all groupings
        if (!currentCoord.getType().equals(CoordType.HIT)) {
          if (!tempGroup.isEmpty()) {
            allGroupings.add(new HitGrouping(tempGroup.size(), orientation, tempGroup));
            tempGroup = new ArrayList<>();
          }
        } else {
          tempGroup.add(currentCoord);

          // if it is the last coordinate in the row/column, and it is a hit, add the group
          if (j == secondBound - 1 && !tempGroup.isEmpty()) {
            allGroupings.add(new HitGrouping(tempGroup.size(), orientation, tempGroup));
            tempGroup = new ArrayList<>();
          }
        }
      }
    }
    List<HitGrouping> output = new ArrayList<>();
    for (HitGrouping hg : allGroupings) {
      if (hg.coords().size() != 1) {
        output.add(hg);
      }
    }
    return output;
  }
}