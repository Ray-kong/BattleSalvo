package cs3500.pa03.singleton;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Singleton Scanner for this program.
 * There should only be one scanner open for the
 * run duration of this program.
 */
public class InputScanner {
  private static InputScanner instance = null;

  private Scanner scanner;

  /**
   * Private constructor for this singleton
   */
  private InputScanner() {
    scanner = new Scanner(System.in);
  }

  /**
   * Gets the instance of this InputScanner
   *
   * @return    the InputScanner singleton
   */
  public static InputScanner getInputScanner() {
    if (instance == null) {
      instance = new InputScanner();
    }
    return instance;
  }

  /**
   * Changes the input stream for this program
   *
   * @param in    input stream to switch this InputScanner to
   */
  public void changeInputStream(InputStream in) {
    this.scanner = new Scanner(in);
  }

  /**
   * Gets the Scanner of this InputScanner
   *
   * @return    Scanner for scanning
   */
  public Scanner getScanner() {
    return this.scanner;
  }
}