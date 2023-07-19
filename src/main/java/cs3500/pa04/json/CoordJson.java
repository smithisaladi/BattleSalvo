package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing Coordinates
 *
 * @param x x
 * @param y y
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

}