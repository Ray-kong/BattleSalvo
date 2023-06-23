package cs3500.pa04.json.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record class that represents a list of coordinates in JSON format.
 *
 * @param coords The list of Coordinates.
 */
public record CoordListJson(
    @JsonProperty("coordinates") CoordJson[] coords) {

}
