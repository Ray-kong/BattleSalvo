package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cs3500.pa03.controller.EndGameController;
import cs3500.pa03.controller.SalvoController;
import cs3500.pa03.model.PlayerUtils;
import org.junit.jupiter.api.Test;

class DriverTest {

  @Test
  public void instantiations() {
    assertDoesNotThrow(() -> new SalvoController());
    assertDoesNotThrow(() -> new EndGameController());
    assertDoesNotThrow(() -> new PlayerUtils());
  }
}