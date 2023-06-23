package cs3500.pa04.json.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record class that represents a single coordinate in JSON format.
 *
 * @param x The x coordinate.
 * @param y The y coordinate.
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

}
