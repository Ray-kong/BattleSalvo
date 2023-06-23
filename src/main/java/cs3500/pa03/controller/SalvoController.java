package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameState;
import cs3500.pa03.model.Ship;
import cs3500.pa03.singleton.InputScanner;
import cs3500.pa03.view.ConsoleView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The responsibility of this controller is to
 * manage method calls that happen during a salvo.
 */
public class SalvoController {

  /**
   * Methods to call in order, for when a salvo happens.
   * "Launches" each players salvo. Recursively calls itself
   * until the game is over.
   *
   * @param state GameState to ascertain data about the game.
   */
  public static void launchSalvo(GameState state) {
    ConsoleView view = new ConsoleView();
    if (isGameOver(state)) {
      EndGameController.endGameSequence(state);
    } else {
      view.displayBoard(state.getP1().getEnemyBoard());
      view.displayBoard(state.getP1().getMyBoard());

      List<Coord> userShots = state.getP1().takeShots();
      List<Coord> computerShots = state.getP2().takeShots();

      List<Coord> hitsOnP1 = state.getP1().reportDamage(computerShots);
      List<Coord> hitsOnP2 = state.getP2().reportDamage(userShots);

      state.getP1().successfulHits(hitsOnP2);
      state.getP2().successfulHits(hitsOnP1);

      launchSalvo(state);
    }
  }

  /**
   * Method to take in a user attack through the console.
   *
   * @param num   number of shots left available
   * @param maxX  max X value for a shot entry
   * @param maxY  max Y value for a shot entry
   * @return      the list of user shots as a list of coords
   */
  public static List<Coord> getUserAttack(int num, int maxX, int maxY) {
    ConsoleView view = new ConsoleView();
    Scanner s = InputScanner.getInputScanner().getScanner();
    List<Coord> shots = new ArrayList<>();
    view.promptCoords(num);
    int x = -1;
    int y = -1;
    for (int i = 0; i < num; i++) {
      x = s.nextInt();
      y = s.nextInt();
      if (x < 0 || y < 0 || x > maxX || y > maxY) {
        break;
      }
      shots.add(new Coord(x, y));
    }
    while (x < 0 || y < 0 || x > maxX || y > maxY) {
      shots = new ArrayList<>();
      view.invalidCoords();
      for (int i = 0; i < num; i++) {
        x = s.nextInt();
        y = s.nextInt();
        if (x < 0 || y < 0 || x > maxX || y > maxY) {
          break;
        }
        shots.add(new Coord(x, y));
      }
    }
    return shots;
  }

  /**
   * Queries if the game is over. This is ascertained by handing
   * this method a state object representing the current
   * state of the game.
   *
   * @param state GameState object representing the state of the game
   * @return      boolean whether or not the game is over
   */
  public static boolean isGameOver(GameState state) {
    int p1ShipsLeft = 0;
    int p2ShipsLeft = 0;

    for (Ship s : state.getP1().getMyShips()) {
      if (!s.isSunk()) {
        p1ShipsLeft++;
      }
    }

    for (Ship s : state.getP2().getMyShips()) {
      if (!s.isSunk()) {
        p2ShipsLeft++;
      }
    }

    return p1ShipsLeft == 0 || p2ShipsLeft == 0;
  }
}