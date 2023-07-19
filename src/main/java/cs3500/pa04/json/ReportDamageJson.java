package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json object representing the damage
 *
 * @param methodName name
 * @param argument args
 */
public record ReportDamageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") ReportDamageArgumentsJson argument
) {

}