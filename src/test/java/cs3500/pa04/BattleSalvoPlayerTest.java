package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.BattleSalvoPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.fleet.Ship;
import cs3500.pa04.model.fleet.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleSalvoPlayerTest {
  private BattleSalvoPlayer player;
  int width;
  int height;
  Map<ShipType, Integer> specs;

  @BeforeEach
  public void setUp() {
    player = new BattleSalvoPlayer();
    width = 6;
    height = 6;
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLE_SHIP, 2);
    specs.put(ShipType.DESTROYER, 2);
    specs.put(ShipType.SUBMARINE, 1);
  }

  @Test
  void setup() {
    List<Ship> ships = player.setup(width, height, specs);
    assertNotNull(ships);
    assertEquals(6, ships.size());
  }

  @Test
  public void testSetupException() {
    player.setup(width, height, specs);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setErr(new PrintStream(outContent));
    player.setup(-1, -1, specs);
    System.setErr(System.err);
    assertTrue(outContent.toString().contains("Exception"));
  }

  @Test
  public void testName() {
    assertNull(player.name());
  }

  @Test
  void takeShots() {
    player.setup(width, height, specs);
    List<Coord> shots = player.takeShots();
    assertNotNull(shots);
  }


  @Test
  void reportDamageWithHits() throws Exception {
    player.setup(width, height, specs);
    player.getMyBoard().setCell(5, 5, "SSS");
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(5, 5));
    List<Coord> damagedShots = player.reportDamage(shots);
    assertNotNull(damagedShots);
    assertEquals(1, damagedShots.size());
    assertEquals(new Coord(5, 5), damagedShots.get(0));
    assertEquals("HHH", player.getMyBoard().getCell(5, 5));
  }

  @Test
  public void testReportDamageWithoutHits() throws Exception {
    player.setup(width, height, specs);
    player.getMyBoard().setCell(5, 5, "---");
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(5, 5));
    List<Coord> damagedShots = player.reportDamage(shots);
    assertTrue(damagedShots.isEmpty());
    assertEquals("MMM", player.getMyBoard().getCell(5, 5));
  }

  @Test
  void successfulHits() throws Exception {
    player.setup(height, width, specs);
    player.getOpponentBoard().setCell(1, 1, "SOO");
    List<Coord> hits = new ArrayList<>();
    hits.add(new Coord(1, 1));
    player.successfulHits(hits);
    assertEquals("HHH", player.getOpponentBoard().getCell(1, 1));
  }

  @Test
  void endGame() {
    GameResult result = GameResult.WIN;
    String reason = "Test reason";

    player.endGame(result, reason);
  }

  @Test
  public void testPrintBoard() throws Exception {
    player.setup(width, height, specs);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    final PrintStream old = System.out;
    System.setOut(printStream);
    player.printBoard();
    System.out.flush();
    System.setOut(old);
    String output = outputStream.toString();

    assertTrue(output.contains("Opponent's board"));
    assertTrue(output.contains("My board"));
  }

  @Test
  void trackHumanShots() throws Exception {
    player.setup(width, height, specs);
    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(5, 5));
    player.trackHumanShots(shots);
    assertEquals("MMM", player.getOpponentBoard().getCell(5, 5));
    shots.clear();
    shots.add(new Coord(5, 5));
    player.trackHumanShots(shots);
    assertEquals("MMM", player.getOpponentBoard().getCell(5, 5));

  }
}