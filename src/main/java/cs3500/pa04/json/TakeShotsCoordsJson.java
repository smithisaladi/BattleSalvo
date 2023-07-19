package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a JSON object for the coordinates of shots to be taken.
 *
 * @param list of shots
 */
public record TakeShotsCoordsJson(
    @JsonProperty("coordinates") List<CoordJson> list
) {

}
