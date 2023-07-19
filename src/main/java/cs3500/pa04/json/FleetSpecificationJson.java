package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing a fleet
 *
 * @param carrier type of ship
 * @param battleShip type of ship
 * @param destroyer type of ship
 * @param submarine type of ship
 */
public record FleetSpecificationJson(
    @JsonProperty("CARRIER") int carrier,
    @JsonProperty("BATTLESHIP") int battleShip,
    @JsonProperty("DESTROYER") int destroyer,
    @JsonProperty("SUBMARINE") int submarine

) {

}
