package cs3500.pa03.model.enumerations;

/**
 * Enumeration Type to represent the outcomes of a game.
 */
public enum GameResult {

  /**
   * Game Win
   */
  WIN,

  /**
   * Game Loss
   */
  LOSS,

  /**
   * Game Draw
   */
  DRAW;

  /**
   * Converts a given GameResult into a String.
   *
   * @param result  GameResult to be turned into a String
   * @return        String version of the given GameResult
   */
  public static String toString(GameResult result) {
    switch (result) {
      case WIN:
        return "Win";
      case LOSS:
        return "Loss";
      default:
        return "Draw";
    }
  }
}