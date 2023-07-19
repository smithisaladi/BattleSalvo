package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json object representing the arguments for report damage
 *
 * @param list of Coordinates
 */
public record ReportDamageArgumentsJson(
    @JsonProperty("coordinates") List<CoordJson> list
) {

}
