package cs3500.pa04.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.data.FleetSpecJson;

/**
 * Record class that represents a setup request in JSON format.
 *
 * @param width The width of the game board.
 * @param height The height of the game board.
 * @param specs The specifications for the fleet.
 */
public record SetupRequestJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpecJson specs) {
}
