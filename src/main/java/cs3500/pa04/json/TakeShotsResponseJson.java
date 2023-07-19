package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON response object for taking shots in a game.
 * It contains the method name and the coordinates of the shots.
 *
 * @param methodName name of the method
 * @param coords of the shot
 */
public record TakeShotsResponseJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") TakeShotsCoordsJson coords
) {

}
