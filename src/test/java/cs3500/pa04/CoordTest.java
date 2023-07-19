package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa04.model.Coord;
import org.junit.jupiter.api.Test;

class CoordTest {

  @Test
  public void testConstructor_validCoordinates() {
    try {
      Coord coord = new Coord(5, 10);
      assertEquals(5, coord.getWidth());
      assertEquals(10, coord.getHeight());
    } catch (Exception e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test
  public void testConstructor_negativeWidth() {
    try {
      Coord coord = new Coord(-5, 10);
      fail("Expected exception not thrown.");
    } catch (Exception e) {
      assertEquals("Invalid Co-ordinates <-5> <10>", e.getMessage());
    }
  }

  @Test
  public void testConstructor_negativeHeight() {
    try {
      Coord coord = new Coord(5, -10);
      fail("Expected exception not thrown.");
    } catch (Exception e) {
      assertEquals("Invalid Co-ordinates <5> <-10>", e.getMessage());
    }
  }

  @Test
  public void testEquals_sameObject() throws Exception {
    Coord coord = new Coord(5, 10);
    assertTrue(coord.equals(coord));
  }

  @Test
  public void testEquals_differentClass() throws Exception {
    Coord coord = new Coord(5, 10);
    assertFalse(coord.equals("not a Coord object"));
  }

  @Test
  public void testEquals_equalCoordinates() throws Exception {
    Coord coord1 = new Coord(5, 10);
    Coord coord2 = new Coord(5, 10);
    assertTrue(coord1.equals(coord2));
  }

  @Test
  public void testEquals_unequalCoordinates() throws Exception {
    Coord coord1 = new Coord(5, 10);
    Coord coord2 = new Coord(10, 5);
    assertFalse(coord1.equals(coord2));
  }

  @Test
  public void testHashCode_equalCoordinates() throws Exception {
    Coord coord1 = new Coord(5, 10);
    Coord coord2 = new Coord(5, 10);
    assertEquals(coord1.hashCode(), coord2.hashCode());
  }

  @Test
  public void testHashCode_unequalCoordinates() throws Exception {
    Coord coord1 = new Coord(5, 10);
    Coord coord2 = new Coord(10, 5);
    assertNotEquals(coord1.hashCode(), coord2.hashCode());
  }
}