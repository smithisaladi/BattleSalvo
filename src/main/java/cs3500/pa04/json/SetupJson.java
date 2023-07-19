package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing the setup of a game
 *
 * @param methodName name
 * @param argument args
 */
public record SetupJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") SetupArgumentsJson argument
) {

}
