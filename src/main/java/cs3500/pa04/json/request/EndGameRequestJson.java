package cs3500.pa04.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record class that represents an end game request in JSON format.
 *
 * @param result The result of the game.
 * @param reason The reason why the game has ended.
 */
public record EndGameRequestJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {
}
