package cs3500.pa04.json.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record class that represents a ship in JSON format.
 *
 * @param coord The Coordinate of the ship.
 * @param length The length of the ship.
 * @param direction The direction of the ship.
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {

}