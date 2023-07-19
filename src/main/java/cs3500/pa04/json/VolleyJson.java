package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a JSON object for a volley in a game, containing the coordinates of a ship.
 *
 * @param coord of the ship
 */
public record VolleyJson(@JsonProperty("ship") CoordJson coord) {

}
