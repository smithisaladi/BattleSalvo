package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json Object for arguments for successful hits
 *
 * @param list of coordinated that are hits
 */
public record SuccessfulHitsArgumentsJson(
    @JsonProperty("coordinates") List<CoordJson> list) {

}