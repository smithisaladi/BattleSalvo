package cs3500.pa04.model;

import cs3500.pa04.model.fleet.Ship;
import cs3500.pa04.model.fleet.ShipType;
import cs3500.pa04.view.BattleSalvoView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a Player of the BattleSalvo game
 */
public class BattleSalvoPlayer implements Player {

  private Board board = null;
  private Board opponentBoard = null;
  public BattleSalvoView view = new BattleSalvoView();

  public BattleSalvoPlayer() {
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public List<Ship> setup(int width, int height, Map<ShipType, Integer> specifications) {
    List<Ship> ships = null;
    try {
      board = new Board(width, height);
      opponentBoard = new Board(width, height);

      board.setFleet(specifications.get(ShipType.CARRIER), specifications.get(ShipType.BATTLE_SHIP),
          specifications.get(ShipType.DESTROYER), specifications.get(ShipType.SUBMARINE));

      ships = board.getFleet().getShips();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ships;
  }

  public boolean isGameOver() {
    return board.getFleet().getNumOfShipsFloating() == 0;
  }

  @Override
  public List<Coord> takeShots() {
    int numOfShipsFloating = board.getFleet().getNumOfShipsFloating();
    List<Coord> listOfCoordinates = new ArrayList<>();

    Random random = new Random();

    try {
      int boardWidth = board.getWidth();
      int boardHeight = board.getHeight();
      boolean[][] alreadyShot = new boolean[boardWidth][boardHeight];
      int totalCells = boardWidth * boardHeight;
      int shotsTaken = 0;

      while (numOfShipsFloating > 0 && shotsTaken < totalCells) {
        int width = random.nextInt(boardWidth);
        int height = random.nextInt(boardHeight);

        if (!alreadyShot[width][height]) {
          String cell = opponentBoard.getCell(width, height);
          if (!cell.equals("HHH") && !cell.equals("MMM")) {
            listOfCoordinates.add(new Coord(width, height));
            opponentBoard.setCell(width, height, "MMM");
            numOfShipsFloating--;
          }
          alreadyShot[width][height] = true;
          shotsTaken++;
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return listOfCoordinates;
  }


  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hits = new ArrayList<Coord>();

    for (Coord cord : opponentShotsOnBoard) {
      int width = cord.getWidth();
      int height = cord.getHeight();

      //System.out.println("Getting cell at " + width + ":" + height);

      String cellValue = board.getCell(width, height);

      if (cellValue == null) {
        // Do nothing if the cell value is null.
      } else if (cellValue.equals("---")) {
        board.setCell(width, height, "MMM");
      } else if (!cellValue.equals("HHH") && !cellValue.equals("MMM")) {
        hits.add(cord);
        board.setCell(width, height, "HHH");
      }
    }
    board.checkIfAllShipsSunk();
    return hits;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord cord : shotsThatHitOpponentShips) {
      int width = cord.getWidth();
      int height = cord.getHeight();
      opponentBoard.setCell(width, height, "HHH");
    }
  }

  @Override
  public void endGame(GameResult result, String reason) {
    view.displayGameResult(result, reason);
  }

  public void printBoard() {
    opponentBoard.print("Opponent's board");
    board.print("My board");
  }

  public Board getMyBoard() {
    return board;
  }

  public Board getOpponentBoard() {
    return opponentBoard;
  }

  /**
   * Tracks human shots
   *
   * @param humanPlayerShots the shots taken by the human player
   */
  public void trackHumanShots(List<Coord> humanPlayerShots) {
    for (Coord cord : humanPlayerShots) {
      int width = cord.getWidth();
      int height = cord.getHeight();
      if (opponentBoard.getCell(width, height).equals("---")) {
        opponentBoard.setCell(width, height, "MMM");
      }
    }
  }
}
