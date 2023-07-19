package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing a response for the set up of a game
 *
 * @param methodName name of the method
 * @param argument   args
 */
public record SetupResponseJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") SetupResponseArgumentsJson argument
) {

}
