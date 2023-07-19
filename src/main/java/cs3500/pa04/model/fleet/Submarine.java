package cs3500.pa04.model.fleet;


import cs3500.pa04.model.fleet.Ship;
import cs3500.pa04.model.fleet.ShipType;

/**
 * Represents the Submarine Ship
 */
public class Submarine extends Ship {

  public Submarine(String id) {
    super(id, ShipType.SUBMARINE, "Submarine", 3);
  }

}
