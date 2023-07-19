package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json Object representing setup arguments
 *
 * @param height of board
 * @param width of board
 * @param fleetSpec num of ships
 */
public record SetupArgumentsJson(
    @JsonProperty("height") int height,
    @JsonProperty("width") int width,
    @JsonProperty("fleet-spec") FleetSpecificationJson fleetSpec
) {

}
