package cs3500.pa03.model;

import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa03.model.enumerations.GameResult;
import cs3500.pa03.model.enumerations.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class implementing the Player interface, representing
 * the ComputerPlayer for this game.
 */
public class ComputerPlayer implements Player {
  String name;
  private Coord[][] myBoard;
  private Coord[][] enemyBoard;
  private List<Ship> myShips;
  private List<Coord> previousShots;

  List<Coord> shotsEveryThree;

  /**
   * Constructor for a ComputerPlayer
   */
  public ComputerPlayer() {
    previousShots = new ArrayList<>();
    name = "pa04-almostthreegoodatcoding";
    shotsEveryThree = new ArrayList<>();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return null;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    myBoard = new Coord[width][height];
    enemyBoard = new Coord[myBoard.length][myBoard[0].length];
    PlayerUtils.initializeBoard(enemyBoard);
    List<Ship> ships = PlayerUtils.setup(specifications, myBoard);
    myShips = ships;

    return ships;
  }

  /**
   * This method uses helper methods to decide where to shoot on the enemy's board.
   * It calculates the total shots, collects all empty spaces and hits,
   * and filters out repeating coordinates and previous shots.
   *
   * @return The list of coordinates to shoot at
   */
  @Override
  public List<Coord> takeShots() {
    ShootingStrategy strategy = new ShootingStrategy(enemyBoard, previousShots, myShips);

    // Shoot!
    return strategy.generateShots();
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return PlayerUtils.reportDamage(opponentShotsOnBoard, myBoard, myShips);
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      enemyBoard[c.getX()][c.getY()] = new Coord(c.getX(), c.getY(), CoordType.HIT);
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

  public List<Ship> getMyShips() {
    return this.myShips;
  }

  public Coord[][] getEnemyBoard() {
    return this.enemyBoard;
  }

  public void setMyShips(List<Ship> list) {
    this.myShips = list;
  }
}
