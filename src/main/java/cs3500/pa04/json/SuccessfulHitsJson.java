package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json object for successful hits
 *
 * @param methodName name of the method
 * @param argument args
 */
public record SuccessfulHitsJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") SuccessfulHitsArgumentsJson argument
) {

}
