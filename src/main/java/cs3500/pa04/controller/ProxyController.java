package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EndGameArgumentsJson;
import cs3500.pa04.json.EndGameResponseArgumentsJson;
import cs3500.pa04.json.EndGameResponseJson;
import cs3500.pa04.json.JoinArgumentsJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamageArgumentsJson;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SetupArgumentsJson;
import cs3500.pa04.json.SetupResponseArgumentsJson;
import cs3500.pa04.json.SetupResponseJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.SuccessfulHitsArgumentsJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.json.TakeShotsCoordsJson;
import cs3500.pa04.json.TakeShotsResponseJson;
import cs3500.pa04.model.BattleSalvoPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.fleet.Ship;
import cs3500.pa04.model.fleet.ShipType;
import cs3500.pa04.utils.JsonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ProxyController that is used to communicate with the server
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final BattleSalvoPlayer player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if
   */
  public ProxyController(Socket server, BattleSalvoPlayer player) throws Exception {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
    run();
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON.
   * When a complete message is sent by the server, the message is parsed and then
   * delegated to the corresponding helper method for each message. This method
   * stops when the connection to the server is closed or an IOException is thrown
   * from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed() && !parser.isClosed()) {
        if (parser.nextToken() == null) {
          break; // Exit the loop if there is no more input
        }

        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      if (!this.server.isClosed()) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      closeConnection();
    }
  }

  /**
   * Closes the connection to the server.
   */
  private void closeConnection() {
    try {
      this.server.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and
   * delegates to the corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  public void delegateMessage(MessageJson message) throws Exception {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    System.out.println("\n\n" + name);
    System.out.println(message);

    if ("join".equals(name)) {
      handleJoin(arguments);
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
      this.player.printBoard();
    } else if ("take-shots".equals(name)) {
      handleTakeShots(arguments);
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
      this.player.printBoard();
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }


  /**
   * Method that handles Json message Join
   *
   * @param arguments args
   */
  public void handleJoin(JsonNode arguments) {
    JoinArgumentsJson arg = new JoinArgumentsJson("smithisaladi", "SINGLE");
    JoinJson join = new JoinJson("join", arg);
    JsonNode jsonResponse = JsonUtils.serializeRecord(join);
    System.out.println("Sending RESPONSE:" + jsonResponse);
    this.out.println(jsonResponse);
  }

  /**
   * Method that handles the Json Message end game
   *
   * @param arguments args
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameArgumentsJson shArgs = this.mapper.convertValue(arguments, EndGameArgumentsJson.class);
    System.out.println("Report End game data  \n" + shArgs);

    // Prepare Response
    EndGameResponseArgumentsJson egargs = new EndGameResponseArgumentsJson();

    EndGameResponseJson shres = new EndGameResponseJson("end-game", egargs);

    // RESPONSE
    JsonNode jsonResponse = JsonUtils.serializeRecord(shres);
    System.out.println("Sending RESPONSE:" + jsonResponse);
    this.out.println(jsonResponse);
    this.player.endGame(shArgs.result(), shArgs.reason());
  }

  /**
   * Method that handles the Json Message successfulhits
   *
   * @param arguments agrs
   * @throws Exception incase an exception is thrown
   */
  public void handleSuccessfulHits(JsonNode arguments) throws Exception {
    List<Coord> list = new ArrayList<Coord>();

    SuccessfulHitsArgumentsJson shArgs =
        this.mapper.convertValue(arguments, SuccessfulHitsArgumentsJson.class);
    System.out.println("Report Damage data  \n" + shArgs);

    if (shArgs.list() != null) {
      for (CoordJson jc : shArgs.list()) {
        list.add(new Coord(jc.x(), jc.y()));
      }
    } else {
      System.out.println("The list in shArgs is null");
    }

    this.player.successfulHits(list);

    // Prepare Response
    List<CoordJson> jclist = new ArrayList<CoordJson>();
    SuccessfulHitsArgumentsJson shargs = new SuccessfulHitsArgumentsJson(jclist);

    SuccessfulHitsJson shres = new SuccessfulHitsJson("successful-hits", shargs);

    // RESPONSE
    JsonNode jsonResponse = JsonUtils.serializeRecord(shres);
    System.out.println("Sending RESPONSE:" + jsonResponse);
    this.out.println(jsonResponse);
  }


  /**
   * Method that handles Json message report damage
   *
   * @param arguments arg
   * @throws Exception incase an exception is thrown
   */
  public void handleReportDamage(JsonNode arguments) throws Exception {
    List<Coord> list = new ArrayList<Coord>();
    List<Coord> hitList = null;

    ReportDamageArgumentsJson setupArgs =
        this.mapper.convertValue(arguments, ReportDamageArgumentsJson.class);
    System.out.println("Report Damage data  \n" + setupArgs);

    if (setupArgs.list() != null) {
      for (CoordJson jc : setupArgs.list()) {
        list.add(new Coord(jc.x(), jc.y()));
      }
      hitList = this.player.reportDamage(list);

      // Prepare Response
      List<CoordJson> jclist = new ArrayList<CoordJson>();
      for (Coord cd : hitList) {
        CoordJson jcd = new CoordJson(cd.getWidth(), cd.getHeight());
        jclist.add(jcd);
      }
      ReportDamageArgumentsJson rda = new ReportDamageArgumentsJson(jclist);

      ReportDamageJson rd = new ReportDamageJson("report-damage", rda);

      JsonNode jsonResponse = JsonUtils.serializeRecord(rd);
      System.out.println("Sending RESPONSE:" + jsonResponse);
      this.out.println(jsonResponse);
    } else {
      System.err.println("Report Damage data list is null.");
    }
  }


  /**
   * Method that handles Json message takeshots
   *
   * @param arguments args
   */
  private void handleTakeShots(JsonNode arguments) {

    List<Coord> shots = this.player.takeShots();
    List<CoordJson> list = new ArrayList<CoordJson>();
    for (Coord cd : shots) {
      CoordJson jcd = new CoordJson(cd.getWidth(), cd.getHeight());
      list.add(jcd);
    }
    TakeShotsCoordsJson coords = new TakeShotsCoordsJson(list);

    TakeShotsResponseJson tsr = new TakeShotsResponseJson("take-shots", coords);

    // RESPONSE
    JsonNode jsonResponse = JsonUtils.serializeRecord(tsr);
    System.out.println("Sending RESPONSE:" + jsonResponse);
    this.out.println(jsonResponse);
  }


  /**
   * Method that handles the Json Message setup
   *
   * @param arguments arguments
   */
  public void handleSetup(JsonNode arguments) {
    SetupArgumentsJson setupArgs = this.mapper.convertValue(arguments, SetupArgumentsJson.class);
    System.out.println("Setup configuration \n" + setupArgs);

    SetupResponseJson sr = setupThePlayer(setupArgs);
    // RESPONSE
    JsonNode jsonResponse = JsonUtils.serializeRecord(sr);
    System.out.println("Sending RESPONSE:" + jsonResponse);
    this.out.println(jsonResponse);
  }

  private SetupResponseJson setupThePlayer(SetupArgumentsJson setupArgs) {
    Map<ShipType, Integer> fleetSizes = new HashMap<ShipType, Integer>();

    fleetSizes.put(ShipType.BATTLE_SHIP, setupArgs.fleetSpec().battleShip());
    fleetSizes.put(ShipType.CARRIER, setupArgs.fleetSpec().carrier());
    fleetSizes.put(ShipType.DESTROYER, setupArgs.fleetSpec().destroyer());
    fleetSizes.put(ShipType.SUBMARINE, setupArgs.fleetSpec().submarine());

    List<Ship> ships = this.player.setup(setupArgs.width(), setupArgs.height(), fleetSizes);

    // RESPONSS
    List<ShipJson> list = new ArrayList<ShipJson>();
    for (Ship ship : ships) {
      Coord c1 = ship.getCellCoordinates().get(0);
      Coord c2 = ship.getCellCoordinates().get(1);

      CoordJson jc = new CoordJson(c1.getWidth(), c1.getHeight());
      ShipJson js = new ShipJson(jc, ship.getSize(),
          (c1.getHeight() == c2.getHeight()) ? "HORIZONTAL" : "VERTICAL");
      list.add(js);
    }

    SetupResponseArgumentsJson sra = new SetupResponseArgumentsJson(list);
    SetupResponseJson sr = new SetupResponseJson("setup", sra);

    return sr;
  }
}
