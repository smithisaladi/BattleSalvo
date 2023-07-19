package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.view.BattleSalvoView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;


class BattleSalvoViewTest {

  @Test
  void testDisplayWelcomeMessage() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    BattleSalvoView view = new BattleSalvoView();
    view.displayWelcomeMessage();
    System.setOut(System.out);

    String expectedOutput = "Hello! Welcome to my OOD BattleSalvo Game!";
    assertEquals(expectedOutput, outputStream.toString().trim());
  }

  @Test
  void testGetBoardSizeInputValidInput() {
    BattleSalvoView view = new BattleSalvoView();
    System.setIn(new ByteArrayInputStream("8\n10\n".getBytes()));
    view.resetScanner();

    int[] boardSize = view.getBoardSizeInput();

    assertEquals(8, boardSize[0]);
    assertEquals(10, boardSize[1]);
  }


  /* @Test
   void testGetFleetSizeInputValidInput() {
     BattleSalvoView view = new BattleSalvoView();
     System.setIn(new ByteArrayInputStream("8\n10\n1\n1\n1\n1".getBytes()));
     view.resetScanner();
     int[] boardSize = {8,10};
     Map<ShipType, Integer> fleetSizes = view.getFleetSizeInput(boardSize);

     assertEquals(1, fleetSizes.get(ShipType.CARRIER));
     assertEquals(1, fleetSizes.get(ShipType.BATTLE_SHIP));
     assertEquals(1, fleetSizes.get(ShipType.DESTROYER));
     assertEquals(1, fleetSizes.get(ShipType.SUBMARINE));
   }*/


  /* @Test
   public void testGetShotsInput_ValidInput() throws Exception {
     BattleSalvoView view = new BattleSalvoView();
     // Set up the mock input stream to provide the input values
     InputStream inputStream = new ByteArrayInputStream("0 0\n1 1\n".getBytes());
     System.setIn(inputStream);

     // Create a mock Board object
     Board board = new Board(10, 10);

     // Call the method being tested
     List<Coord> shots = view.getShotsInput(board);

     // Verify the returned shots
     assertEquals(2, shots.size());
     assertEquals(0, shots.get(0).getWidth());
     assertEquals(0, shots.get(0).getHeight());
     assertEquals(1, shots.get(1).getWidth());
     assertEquals(1, shots.get(1).getHeight());
   }
 */

  @Test
  void displayGameResult() {
  }

  @Test
  void resetScanner() {
  }

  @Test
  void displayShotStatistics() {
  }
}