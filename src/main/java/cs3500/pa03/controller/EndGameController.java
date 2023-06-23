package cs3500.pa03.controller;

import cs3500.pa03.model.GameState;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumerations.GameResult;

/**
 * The responsibility of this controller is to
 * manage the actions of a server during an endgame
 * situation.
 */
public class EndGameController {

  /**
   * Sequence of method calls when the game is ending.
   *
   * @param state State of the game
   */
  public static void endGameSequence(GameState state) {
    int p1ShipsLeft = 0;
    int p2ShipsLeft = 0;

    for (Ship s : state.getP2().getMyShips()) {
      if (!s.isSunk()) {
        p2ShipsLeft++;
      }
    }

    for (Ship s : state.getP1().getMyShips()) {
      if (!s.isSunk()) {
        p1ShipsLeft++;
      }
    }

    if (p1ShipsLeft == 0 && p2ShipsLeft == 0) {
      state.getP1().endGame(GameResult.DRAW, "Salvo ended in mutual destruction.");
      state.getP2().endGame(GameResult.DRAW, "Salvo ended in mutual destruction.");
    } else if (p1ShipsLeft == 0) {
      state.getP1().endGame(GameResult.LOSS, "Salvo ended in your destruction.");
      state.getP2().endGame(GameResult.WIN, "You won!");
    } else {
      state.getP1().endGame(GameResult.WIN, "You won!");
      state.getP2().endGame(GameResult.LOSS, "Salvo ended in your destruction.");
    }
  }
}