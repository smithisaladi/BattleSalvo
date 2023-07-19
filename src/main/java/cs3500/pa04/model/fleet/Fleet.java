package cs3500.pa04.model.fleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the Fleet of ships the Game is played with
 */
public class Fleet {

  List<Ship.Carrier> carrierList = new ArrayList<Ship.Carrier>();
  List<Ship.Battleship> battleshipList = new ArrayList<Ship.Battleship>();
  List<Ship.Destroyer> destroyerList = new ArrayList<Ship.Destroyer>();
  List<Ship.Submarine> submarineList = new ArrayList<Ship.Submarine>();

  /**
   * Constructor
   *
   * @param carrierSize    Number of Carriers
   * @param battleShipSize Number of BattleShips
   * @param destroyerSize  Number of Destroyers
   * @param sumbarineSize  Number of Submarines
   * @throws Exception if the number is invalid
   */
  public Fleet(int carrierSize, int battleShipSize, int destroyerSize, int sumbarineSize)
      throws Exception {
    if (carrierSize <= 0 || battleShipSize <= 0 || destroyerSize <= 0 || sumbarineSize <= 0) {
      throw new Exception(
          "Invalid fleet size Carrier size <" + carrierSize + ">" + "Battle Ship size <"
              + battleShipSize + ">" + "Destroyer size <" + destroyerSize + ">" + "Submarine size <"
              + sumbarineSize + ">");
    }

    for (int index = 0; index < carrierSize; index++) {
      Ship.Carrier carrier = new Ship.Carrier("C" + String.format("%02d", index));
      carrierList.add(carrier);
    }
    for (int index = 0; index < battleShipSize; index++) {
      Ship.Battleship battleship = new Ship.Battleship("B" + String.format("%02d", index));
      battleshipList.add(battleship);
    }
    for (int index = 0; index < destroyerSize; index++) {
      Ship.Destroyer destroyer = new Ship.Destroyer("D" + String.format("%02d", index));
      destroyerList.add(destroyer);
    }
    for (int index = 0; index < sumbarineSize; index++) {
      Ship.Submarine submarine = new Ship.Submarine("S" + String.format("%02d", index));
      submarineList.add(submarine);
    }
  }

  public List<Ship.Carrier> getCarrierList() {
    return carrierList;
  }

  public void setCarrierList(List<Ship.Carrier> carrierList) {
    this.carrierList = carrierList;
  }

  public List<Ship.Battleship> getBattleshipList() {
    return battleshipList;
  }

  public void setBattleshipList(List<Ship.Battleship> battleshipList) {
    this.battleshipList = battleshipList;
  }

  public List<Ship.Destroyer> getDestroyerList() {
    return destroyerList;
  }

  public void setDestroyerList(List<Ship.Destroyer> destroyerList) {
    this.destroyerList = destroyerList;
  }

  public List<Ship.Submarine> getSubmarineList() {
    return submarineList;
  }

  public void setSubmarineList(List<Ship.Submarine> submarineList) {
    this.submarineList = submarineList;
  }

  /**
   * Chooses a ship to be places
   *
   * @return a ship
   */
  public Ship getShipToBePlaced() {
    Ship retShip = null;
    List<String> list = new ArrayList<String>();
    Random random = new Random();

    for (Ship ship : getCarrierList()) {
      if (!ship.isPlacedOnTheBoat()) {
        list.add(ship.getId());
      }
    }
    for (Ship ship : getBattleshipList()) {
      if (!ship.isPlacedOnTheBoat()) {
        list.add(ship.getId());
      }
    }
    for (Ship ship : getDestroyerList()) {
      if (!ship.isPlacedOnTheBoat()) {
        list.add(ship.getId());
      }
    }
    for (Ship ship : getSubmarineList()) {
      if (!ship.isPlacedOnTheBoat()) {
        list.add(ship.getId());
      }
    }

    if (list.size() > 0) {
      int select = random.nextInt(list.size());
      retShip = getShipById(list.get(select));
    }
    return retShip;
  }

  /**
   * Getter method for ships by using a string id
   *
   * @param string id of the ship
   * @return the ship
   */
  public Ship getShipById(String string) {
    Ship retShip = null;

    for (Ship ship : getCarrierList()) {
      if (ship.getId().equals(string)) {
        retShip = ship;
        break;
      }
    }

    if (retShip == null) {
      for (Ship ship : getBattleshipList()) {
        if (ship.getId().equals(string)) {
          retShip = ship;
          break;
        }
      }
    }

    if (retShip == null) {
      for (Ship ship : getDestroyerList()) {
        if (ship.getId().equals(string)) {
          retShip = ship;
          break;
        }
      }
    }
    if (retShip == null) {

      for (Ship ship : getSubmarineList()) {
        if (ship.getId().equals(string)) {
          retShip = ship;
          break;
        }
      }
    }
    return retShip;
  }

  /**
   * Getter method for ships
   *
   * @return ships
   */
  public List<Ship> getShips() {
    List<Ship> ships = new ArrayList<Ship>();

    for (Ship ship : getCarrierList()) {
      ships.add(ship);
    }

    for (Ship ship : getBattleshipList()) {
      ships.add(ship);
    }

    for (Ship ship : getDestroyerList()) {
      ships.add(ship);
    }

    for (Ship ship : getSubmarineList()) {
      ships.add(ship);
    }

    return ships;
  }

  /**
   * The number of floating ships left
   *
   * @return number of unsunk ships
   */
  public int getNumOfShipsFloating() {
    int count = 0;

    for (Ship ship : getCarrierList()) {
      if (ship.isAfloat()) {
        count++;
      }
    }
    for (Ship ship : getBattleshipList()) {
      if (ship.isAfloat()) {
        count++;
      }
    }
    for (Ship ship : getDestroyerList()) {
      if (ship.isAfloat()) {
        count++;
      }
    }
    for (Ship ship : getSubmarineList()) {
      if (ship.isAfloat()) {
        count++;
      }
    }
    return count;
  }
}