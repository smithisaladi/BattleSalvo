package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing the end game
 *
 * @param methodName name
 * @param argument args
 */
public record EndGameJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") EndGameArgumentsJson argument) {
}
