package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing the end game response
 *
 * @param methodName name
 * @param argument args
 */
public record EndGameResponseJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") EndGameResponseArgumentsJson argument) {
}
