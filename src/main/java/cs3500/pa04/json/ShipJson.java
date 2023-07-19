package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json object representing a ship
 *
 * @param coord location of the ship
 * @param length size of the ship
 * @param direction alignment of the ship
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {

}
