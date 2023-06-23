package cs3500.pa03.singleton;

import java.util.Random;

/**
 * Singleton Randomizer for generating
 * random numbers.
 */
public class Randomizer {
  private static Randomizer instance = null;

  //in tester rand, Random gets a set seed.
  private Random random;

  /**
   * Private constructor for this Randomizer
   */
  private Randomizer() {
    random = new Random();
  }

  /**
   * Gets the Randomizer Singleton
   *
   * @return  Randomizer Singleton object
   */
  public static Randomizer getRandomizer() {
    if (instance == null) {
      instance = new Randomizer();
    }
    return instance;
  }

  /**
   * Updates the seed of this Randomizer object
   * for testing purposes.
   *
   * @param seed int seed for the random object
   */
  public void updateSeed(int seed) {
    random = new Random(seed);
  }

  /**
   *  Resets the random back to one without a seed
   */
  public void updateSeed() {
    random = new Random();
  }

  /**
   * Gets the Random object of this Randomizer
   *
   * @return    Random object
   */
  public Random getRandom() {
    return this.random;
  }
}
