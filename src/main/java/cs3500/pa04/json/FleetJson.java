package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json Object representing a Fleet
 *
 * @param ships the ships in a fleet
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> ships) {

}
