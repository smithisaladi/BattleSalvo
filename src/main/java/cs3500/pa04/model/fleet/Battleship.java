package cs3500.pa04.model.fleet;

/**
 *  Represents the BattleShip
 */
public class Battleship extends Ship {

  public Battleship(String id) {
    super(id, ShipType.BATTLE_SHIP, "Battleship", 5);
  }
}
