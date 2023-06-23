package cs3500.pa03.controller;

import cs3500.pa03.model.ComputerPlayer;
import cs3500.pa03.model.GameState;
import cs3500.pa03.model.UserPlayer;
import cs3500.pa03.model.enumerations.ShipType;
import cs3500.pa03.singleton.InputScanner;
import cs3500.pa03.view.ConsoleView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The responsibility of this controller is to
 * manage what happens at the start of the game.
 */
public class GameController {

  /**
   * Method calls to initialize the game.
   */
  public void initializeGame() {
    GameState state = new GameState();
    initializeUser(state.getP1(), state);
    initializeComputer(state.getP2(), state);
    SalvoController.launchSalvo(state);
  }

  /**
   * Initializing a console player.
   *
   * @param p     UserPlayer to init
   * @param state Game state to init
   */
  private void initializeUser(UserPlayer p, GameState state) {
    ConsoleView view = new ConsoleView();
    view.welcome();
    Scanner s = InputScanner.getInputScanner().getScanner();
    view.promptBoardSize();
    int width = s.nextInt();
    int height = s.nextInt();
    while (!(validBoardRange(width) && validBoardRange(height))) {
      view.invalidBoardSize(6, 15);
      width = s.nextInt();
      height = s.nextInt();
    }
    //-----------------------//
    Map<ShipType, Integer> specs = askSpecifications(height, width);
    state.setHeight(height);
    state.setWidth(width);
    state.setSpecifications(specs);
    p.setup(height, width, specs);
  }

  /**
   * Prompts the user for fleet specifications.
   *
   * @param height height of the board
   * @param width  width of the board
   * @return       HashMap representing the number of ships per type
   */
  private Map<ShipType, Integer> askSpecifications(int height, int width) {
    ConsoleView view = new ConsoleView();
    Scanner s = InputScanner.getInputScanner().getScanner();
    view.promptShips(Math.min(width, height));
    Map<ShipType, Integer> specs = new HashMap<>();
    List<ShipType> types = ShipType.asList();
    int total = 0;
    List<Integer> entries = new ArrayList<>();
    for (ShipType t : types) {
      int temp = s.nextInt();
      entries.add(temp);
      total += temp;
    }
    while (total <= 0 || total > Math.min(width, height) || entries.contains(0)) {
      specs = new HashMap<>();
      entries = new ArrayList<>();
      total = 0;
      view.invalidShips(Math.min(width, height));
      for (ShipType t : types) {
        int temp = s.nextInt();
        entries.add(temp);
        total += temp;
      }
    }
    for (int i = 0; i < types.size(); i++) {
      specs.put(types.get(i), entries.get(i));
    }
    return specs;
  }

  /**
   * Is the given number within this set range? 6 - 15 inclusive
   *
   * @param n given number to test
   * @return  boolean
   */
  private boolean validBoardRange(int n) {
    return n >= 6 && n <= 15;
  }

  /**
   * Initializes the computer player with its setup method.
   *
   * @param p     ComputerPlayer to initialize
   * @param state GameState to initialize
   */
  private void initializeComputer(ComputerPlayer p, GameState state) {
    p.setup(state.getHeight(), state.getWidth(), state.getSpecifications());
  }
}