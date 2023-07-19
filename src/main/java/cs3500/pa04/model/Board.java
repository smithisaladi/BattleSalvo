package cs3500.pa04.model;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.fleet.Fleet;
import cs3500.pa04.model.fleet.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 * Represents the board the game is played on
 */
public class Board {

  private String[][] board = null;
  private int height = -1;
  private int width = -1;
  private Fleet fleet = null;

  /**
   * Creats a board
   *
   * @param width the width of the board
   * @param height the height of the board
   * @throws Exception if the height and width arent valid
   */
  public Board(int width, int height) throws Exception {

    if (height < 6 || height > 15 || width < 6 || width > 15) {
      throw new Exception("Invalid board size " + height + " " + width);
    }

    this.width = width;
    this.height = height;

    // Create board of size x by y
    board = new String[width][height];

    for (int index = 0; index < width; index++) {
      for (int index1 = 0; index1 < height; index1++) {
        board[index][index1] = "---"; // Empty
      }
    }
  }

  public Fleet getFleet() {
    return fleet;
  }

  /**
   * Setter method for the Fleet
   *
   * @param carrierSize number of Carrier ships
   * @param battleShipSize number of Battle ships
   * @param destroyerSize number of Destroyer ships
   * @param sumbarineSize number of submarines
   * @throws Exception if the fleet sizes arent valid
   */
  public void setFleet(int carrierSize, int battleShipSize, int destroyerSize, int sumbarineSize)
      throws Exception {

    int small = (height < width) ? height : width;

    if (carrierSize + battleShipSize + destroyerSize + sumbarineSize > small) {
      throw new Exception("Please enter your fleet in the order "
          + "[Carrier, Battleship, Destroyer, Submarine].\r\n"
          + "Remember, your fleet may not exceed size " + small);
    }

    if (carrierSize <= 0 || battleShipSize <= 0 || destroyerSize <= 0 || sumbarineSize <= 0) {
      throw new Exception("Uh Oh! You've entered invalid fleet sizes.\r\n"
          + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\r\n"
          + "Remember, your fleet may not exceed size " + small);
    }

    this.fleet = new Fleet(carrierSize, battleShipSize, destroyerSize, sumbarineSize);

    assignFleetToBoard();
  }

  public void setFleet(Fleet fleet) {
    this.fleet = fleet;
  }

  private void assignFleetToBoard() throws Exception {
    Ship ship = null;

    while ((ship = fleet.getShipToBePlaced()) != null) {
      //System.out.println("Adding Ship :" + ship.getName());
      //System.out.println ("Adding ship : " + ship.getId());
      addShipToBoard(ship);
    }
  }

  private void addShipToBoard(Ship ship) throws Exception {
    Random randomNum = new Random();

    while (!ship.isPlacedOnTheBoat()) {
      int width = randomNum.nextInt(board.length);
      int height = randomNum.nextInt(board[0].length);
      boolean isHorizontal = randomNum.nextBoolean();

      Coord coord = new Coord(width, height);

      addShipToBoard(ship, coord, isHorizontal);
    }
  }

  private boolean addShipToBoard(Ship ship, Coord coord, boolean isHorizontal) throws Exception {
    boolean added = false;
    int size = ship.getSize();
    Coord nc = null;
    List<Coord> cellCoordinates = new ArrayList<Coord>();

    if (isHorizontal) {
      if ((nc = getStartingCellHorizontal(ship, coord)) != null) {
        for (int index = 0; index < size; index++) {
          board[nc.getWidth() + index][nc.getHeight()] = ship.getId();
          cellCoordinates.add(new Coord(nc.getWidth() + index, nc.getHeight()));
        }
        ship.setCellCoordinates(cellCoordinates);
        added = true;
        ship.setPlacedOnTheBoat(true);
      }
    } else {
      if ((nc = getStartingCellVertical(ship, coord)) != null) {
        for (int index = 0; index < size; index++) {
          board[nc.getWidth()][nc.getHeight() + index] = ship.getId();
          cellCoordinates.add(new Coord(nc.getWidth(), nc.getHeight() + index));
        }
        ship.setCellCoordinates(cellCoordinates);
        added = true;
        ship.setPlacedOnTheBoat(true);
      }
    }

    return added;
  }

  private Coord getStartingCellHorizontal(Ship ship, Coord coord) throws Exception {
    Coord nc = null;
    int size = ship.getSize();
    int count = 0;

    for (int index = 0; coord.getWidth() + index < width && index < size; index++) {
      if (!board[coord.getWidth() + index][coord.getHeight()].equals("---")) {
        break;
      } else {
        count++;
      }
    }

    if (count == size) {
      nc = new Coord(coord.getWidth(), coord.getHeight());
    } else {
      int index = 0;
      for (index = 0; coord.getWidth() - 1 - index < width && coord.getWidth()
          - 1 - index >= 0 && index < size
          && count < size; index++) {
        if (!board[coord.getWidth() - 1 - index][coord.getHeight()].equals("---")) {
          break;
        } else {
          count++;
        }
      }

      if (count == size) {
        nc = new Coord(coord.getWidth() - index, coord.getHeight());
      }
    }

    return nc;
  }

  private Coord getStartingCellVertical(Ship ship, Coord coord) throws Exception {
    Coord nc = null;
    int size = ship.getSize();
    int count = 0;

    for (int index = 0; coord.getHeight() + index < height && index < size; index++) {
      if (!board[coord.getWidth()][coord.getHeight() + index].equals("---")) {
        break;
      } else {
        count++;
      }
    }

    if (count == size) {
      nc = new Coord(coord.getWidth(), coord.getHeight());
    } else {
      int index = 0;
      for (index = 0; coord.getHeight() - 1 - index < height && coord.getHeight()
          - 1 - index >= 0 && index < size
          && count < size; index++) {
        if (!board[coord.getWidth()][coord.getHeight() - 1 - index].equals("---")) {
          break;
        } else {
          count++;
        }
      }

      if (count == size) {
        nc = new Coord(coord.getWidth(), coord.getHeight() - index);
      }
    }

    return nc;
  }

  /**
   * To print out messages to use on cells
   *
   * @param msg the messgae to be printed
   */
  public void print(String msg) {
    //System.out.println(getWidth ()+ ":" + getHeight ());
    System.out.println(msg);
    System.out.println();

    System.out.print("   ");
    for (int height = 0; height < getHeight(); height++) {
      System.out.print("  " + String.format("%03d", height));
    }
    System.out.println("\n");

    for (int width = 0; width < getWidth(); width++) {
      System.out.print(String.format("%03d", width));
      for (int height = 0; height < getHeight(); height++) {
        System.out.print("  " + board[width][height]);
      }
      System.out.println("\n");
    }
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Getter method for the Cell
   *
   * @param width of the board
   * @param height of the board
   * @return a String of the value
   */
  public String getCell(int width, int height) {
    String value = null;
    if (width >= 0 && height >= 0 && width < getWidth() && height < getHeight()) {
      value = board[width][height];
    }
    return value;
  }


  /**
   * Sets a value to a cell
   *
   * @param width the width of the board
   * @param height the height of the board
   * @param value the value to set the cell to
   * @return return the value
   */
  public String setCell(int width, int height, String value) {
    if (width < getWidth() && height < getHeight()) {
      board [width][height] = value;
    }
    return value;
  }

  /**
   * Checks if all the ships are sunk or not
   */
  public void checkIfAllShipsSunk() {
    List<Ship> ships = this.fleet.getShips();
    List<Coord> coords = null;

    for (Ship ship : ships) {
      coords = ship.getCellCoordinates();

      boolean hit = true;
      for (Coord cd : coords) {
        if (!board[cd.getWidth()][cd.getHeight()].equals("HHH")) {
          hit = false;
          break;
        }
      }
      if (hit) {
        ship.setAfloat(false);
      }
    }
  }
}