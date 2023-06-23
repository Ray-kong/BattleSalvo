package cs3500.pa04.json.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record class that represents list of fleet in JSON format.
 *
 * @param fleet The list representing fleet.
 */

public record FleetJson(
      @JsonProperty("fleet") ShipJson[] fleet) {

}
