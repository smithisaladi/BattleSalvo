package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * Json Object representing the argument to end a game
 *
 * @param result a GameResult
 * @param reason a String
 */
public record EndGameArgumentsJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason
) {

}
