package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Objects representing the arguments to join a game
 *
 * @param name name
 * @param gameType gametype
 */
public record JoinArgumentsJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {

}
