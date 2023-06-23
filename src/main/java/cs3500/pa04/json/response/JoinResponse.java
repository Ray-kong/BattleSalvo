package cs3500.pa04.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record class that represents a response for a join operation.
 *
 * @param name The name of the player who is joining.
 * @param gameType The type of the game the player is joining.
 */
public record JoinResponse(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType){
}
