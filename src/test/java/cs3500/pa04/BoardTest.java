package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa04.model.Board;
import org.junit.jupiter.api.Test;

class BoardTest {
  @Test
  public void testConstructor_validBoardSize() {
    try {
      Board board = new Board(10, 8);
      assertEquals(10, board.getWidth());
      assertEquals(8, board.getHeight());
    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testConstructor_invalidBoardSize() {
    try {
      Board board = new Board(4, 20);
      fail("Expected exception not thrown.");
    } catch (Exception e) {
      assertEquals("Invalid board size 20 4", e.getMessage());
    }
  }

  @Test
  public void testSetFleet_validFleetSizes() {
    try {
      Board board = new Board(10, 10);
      board.setFleet(1, 2, 3, 4);
      assertNotNull(board.getFleet());
    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testSetFleet_invalidFleetSizes() {
    try {
      Board board = new Board(10, 10);
      board.setFleet(3, 3, 3, 3);
      fail("Expected exception not thrown.");
    } catch (Exception e) {
      assertEquals("Please enter your fleet in the order [Carrier, Battleship, Destroyer, "
          + "Submarine].\r\nRemember, your fleet may not exceed size 10", e.getMessage());
    }
  }

  @Test
  public void testSetFleet_invalidFleetSizes_negativeValues() {
    try {
      Board board = new Board(10, 10);
      board.setFleet(-1, 2, -3, 4);
      fail("Expected exception not thrown.");
    } catch (Exception e) {
      assertEquals("Uh Oh! You've entered invalid fleet sizes.\r\nPlease enter your fleet"
              + " in the order [Carrier, Battleship, Destroyer, Submarine].\r\nRemember, "
              + "your fleet may not exceed size 10",
          e.getMessage());
    }
  }
}