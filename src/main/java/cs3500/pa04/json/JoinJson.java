package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing joining a game
 *
 * @param methodName name
 * @param argument args
 */
public record JoinJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JoinArgumentsJson argument) {

}
