package cs3500.pa04.view;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.fleet.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *  Represents the View component of the BattleSalvo Game
 */
public class BattleSalvoView {
  private Scanner scanner;

  public BattleSalvoView() {
    scanner = new Scanner(System.in);
  }


  public void displayWelcomeMessage() {
    System.out.println("Hello! Welcome to my OOD BattleSalvo Game!");
  }

  /**
   * Asks the user for inout on the Board Size
   *
   * @return an array of the boardsize
   */
  public int[] getBoardSizeInput() {
    System.out.println("Please enter a valid height and width below:");

    int height = 0;
    int width = 0;
    boolean validInput = false;

    while (!validInput) {
      try {
        width = this.scanner.nextInt();
        height = this.scanner.nextInt();

        if (height >= 6 && height <= 15 && width >= 6 && width <= 15) {
          validInput = true;
        } else {
          System.out.println("Uh Oh! You've entered invalid dimensions. "
              + "Please remember that the height "
              + "and width of the game must be in the range [6, 15], inclusive. Try again!");
        }
      } catch (NoSuchElementException e) {
        System.out.println("Uh Oh! You've entered invalid dimensions. "
            + "Please remember that the height "
            + "and width of the game must be in the range [6, 15], inclusive. Try again!");
      }
    }
    return new int[] { width, height };
  }

  /**
   * Asks the user for input on the Fleet Sizes
   *
   * @param boardSize the size of the board
   * @return A Hashmap of types of ships along with their frequency
   */
  public Map<ShipType, Integer> getFleetSizeInput(int[] boardSize) {
    int height = boardSize[0];
    int width = boardSize[1];
    int limit = Math.min(height, width);

    System.out.println("Please enter your fleet in the order "
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + limit);

    int[] fleetSizes = new int[4];
    Map<ShipType, Integer> specifications = new HashMap<>();

    boolean validInput = false;
    while (!validInput) {
      try {
        for (int i = 0; i < 4; i++) {
          fleetSizes[i] = scanner.nextInt();
        }
        scanner.nextLine(); // Consume the newline character
        if (fleetSizes[0] + fleetSizes[1] + fleetSizes[2] + fleetSizes[3] <= limit
            && fleetSizes[0] > 0 && fleetSizes[1] > 0
            && fleetSizes[2] > 0 && fleetSizes[3] > 0) {
          validInput = true;
        } else {
          System.out.println("Uh Oh! You've entered invalid fleet sizes.\n");
        }
      } catch (NoSuchElementException e) {
        System.out.println("Uh Oh! You've entered invalid fleet sizes.\n");
        scanner.nextLine(); // Clear the input buffer
      }
    }

    specifications.put(ShipType.CARRIER, fleetSizes[0]);
    specifications.put(ShipType.BATTLE_SHIP, fleetSizes[1]);
    specifications.put(ShipType.DESTROYER, fleetSizes[2]);
    specifications.put(ShipType.SUBMARINE, fleetSizes[3]);

    return specifications;
  }

  /**
   * Asks the user for shots
   *
   * @param board The gameboard to which the shots are received
   * @return A list of coordinates of the shots
   * @throws Exception if the shots arent valid
   */
  public List<Coord> getShotsInput(Board board) throws Exception {
    List<Coord> shots = new ArrayList<>();
    int shipsUnSunk = board.getFleet().getNumOfShipsFloating();
    System.out.println("Please enter " + shipsUnSunk + " shots.");

    for (int i = 0; i < shipsUnSunk; i++) {
      try {
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        if (x >= board.getWidth()  || y >= board.getHeight()) {
          System.out.println(x + ":" + y + " Invalid input. Please enter correct coordinates");
          scanner.nextLine(); // Clear the input buffer
          i--;
          continue;
        }
        shots.add(new Coord(x, y));
      } catch (NoSuchElementException e) {
        System.out.println("Invalid input. Please enter numeric coordinates.");
        scanner.nextLine(); // Clear the input buffer
        i--; // Retry the current shot
        continue;
      }
    }
    scanner.nextLine(); // Clear the input buffer

    //print(shots);
    return shots;
  }

  private void print(List<Coord> shots) {
    for (Coord cd : shots) {
      System.out.println("Coordinates : " + cd.getWidth() + ":" + cd.getHeight());
    }
  }

  /**
   * Displays the results of the Game
   *
   * @param result Enum for the game result
   * @param reason the reason for the game ending
   */
  public void displayGameResult(GameResult result, String reason) {
    System.out.println("Game Over! Result: " + result);
    if (!reason.isEmpty()) {
      System.out.println("Reason: " + reason);
    }
  }

  public void resetScanner() {
    scanner = new Scanner(System.in);
  }

  /**
   * Displays the statistics after shots are exchanged
   *
   * @param humanHit Successful shots by humans
   * @param humanMiss unsuccessful shots by humans
   * @param aiHit successful shots by AI
   * @param aiMiss unsuccessful shots by AI
   */
  public void displayShotStatistics(int humanHit, int humanMiss, int aiHit, int aiMiss) {
    System.out.println("Your Hit Shots " + humanHit);
    System.out.println("Your Miss Shots " + humanMiss);
    System.out.println("AI Hit Shots " + aiHit);
    System.out.println("AI Miss Shots " + aiMiss);
  }
}









