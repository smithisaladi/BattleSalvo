package cs3500.pa04.model.fleet;

import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * Represents a ship
 */

public class Ship {

  private String id;
  private String name;
  private ShipType type;
  private int size;
  private boolean afloat = true;
  private boolean placedOnTheBoat = false;
  private List<Coord> cellCoordinates = null;


  /**
   * The Constructor
   *
   * @param id ship id
   * @param shipType type
   * @param name name
   * @param size size
   */
  public Ship(String id, ShipType shipType, String name, int size) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.type = shipType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public boolean isAfloat() {
    return afloat;
  }

  public void setAfloat(boolean afloat) {
    this.afloat = afloat;
  }

  public boolean isPlacedOnTheBoat() {
    return placedOnTheBoat;
  }

  public void setPlacedOnTheBoat(boolean placedOnTheBoat) {
    this.placedOnTheBoat = placedOnTheBoat;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ShipType getType() {
    return type;
  }

  public void setType(ShipType type) {
    this.type = type;
  }

  public List<Coord> getCellCoordinates() {
    return cellCoordinates;
  }

  public void setCellCoordinates(List<Coord> cellCoordinates) {
    this.cellCoordinates = cellCoordinates;
  }

  /**
   * Represents A Carrier ship
   */
  public static class Carrier extends Ship {

    public Carrier(String id) {
      super(id, ShipType.CARRIER, "Carrier", 6);
    }
  }

  /**
   * Represents the Destroyer ship
   */
  public static class Destroyer extends Ship {

    public Destroyer(String id) {
      super(id, ShipType.DESTROYER, "Destroyer", 4);
    }
  }

  /**
   * Represents the Submarine Ship
   */
  public static class Submarine extends Ship {

    public Submarine(String id) {
      super(id, ShipType.SUBMARINE, "Submarine", 3);
    }

  }

  /**
   *  Represents the BattleShip
   */
  public static class Battleship extends Ship {

    public Battleship(String id) {
      super(id, ShipType.BATTLE_SHIP, "Battleship", 5);
    }
  }
}