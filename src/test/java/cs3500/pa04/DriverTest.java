package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.adapter.CoordAdapter;
import cs3500.pa04.adapter.ShipAdapter;
import org.junit.jupiter.api.Test;

/**
 * Testing new from PA04
 */
public class DriverTest {

  /**
   * Instantiates adapter classes
   */
  @Test
  void instantiations() {
    ShipAdapter sa = new ShipAdapter();
    CoordAdapter ca = new CoordAdapter();
  }

  /**
   * Tests parsing of string args
   */
  @Test
  void driverParseArgs() {
    String[] args = new String[] {"0.0.0.0", "hello"};
    assertThrows(IllegalArgumentException.class, () -> Driver.main(args));
    String[] args2 = new String[] {"0.0.0.0", "35001"};
    assertThrows(RuntimeException.class, () -> Driver.main(args2));
  }
}
