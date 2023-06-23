package cs3500.pa04.json.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.enumerations.ShipType;
import java.util.HashMap;
import java.util.Map;


/**
 * Record class that represents a fleet spec in JSON format.
 *
 * @param numCarrier    The number of Carrier.
 * @param numBattleship The number of Battleship.
 * @param numDestroyer  The number of Destroyer.
 * @param numSubmarine  The number of Submarine.
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int numCarrier,
    @JsonProperty("BATTLESHIP") int numBattleship,
    @JsonProperty("DESTROYER") int numDestroyer,
    @JsonProperty("SUBMARINE") int numSubmarine) {

  /**
   * This method returns the specifications of this FleetSpecJson
   * object.
   *
   * @return HashMap of the fleet specifications.
   */
  public Map<ShipType, Integer> getSpecifications() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, numCarrier);
    specifications.put(ShipType.BATTLESHIP, numBattleship);
    specifications.put(ShipType.DESTROYER, numDestroyer);
    specifications.put(ShipType.SUBMARINE, numSubmarine);

    return specifications;
  }
}
