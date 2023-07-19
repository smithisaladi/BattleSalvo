package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json object representing the setup response args
 *
 * @param list of Json Object ships
 */
public record SetupResponseArgumentsJson(
    @JsonProperty("fleet") List<ShipJson> list
) {

}
