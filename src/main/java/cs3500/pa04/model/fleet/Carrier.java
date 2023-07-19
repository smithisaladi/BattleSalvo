package cs3500.pa04.model.fleet;

/**
 * Represents A Carrier ship
 */
public class Carrier extends Ship {

  public Carrier(String id) {
    super(id, ShipType.CARRIER, "Carrier", 6);
  }
}
