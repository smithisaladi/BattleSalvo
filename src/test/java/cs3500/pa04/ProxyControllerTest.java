package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.model.BattleSalvoPlayer;
import cs3500.pa04.model.Coord;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for The Proxy Controller
 */
public class ProxyControllerTest {

  private ProxyController proxyController;
  private Socket mockSocket;
  private BattleSalvoPlayer mockPlayer;
  private ByteArrayOutputStream outputStream;

  /**
   * SetUp Method that initiates conditions for testing
   *
   * @throws Exception incase an exception is thrown
   */
  @BeforeEach
  public void setUp() throws Exception {
    mockSocket = mock(Socket.class);
    mockPlayer = mock(BattleSalvoPlayer.class);

    InputStream inputStream = new ByteArrayInputStream("".getBytes());
    outputStream = new ByteArrayOutputStream();

    when(mockSocket.getInputStream()).thenReturn(inputStream);
    when(mockSocket.getOutputStream()).thenReturn(outputStream);
    when(mockSocket.isClosed()).thenReturn(true);

    proxyController = new ProxyController(mockSocket, mockPlayer);
  }

  @Test
  public void testHandleJoin() throws IOException {
    proxyController.handleJoin(null);
    String output = outputStream.toString();
    assertTrue(output.contains("smithisaladi"), "Expected 'smithisaladi' in the output");
  }


  @Test
  public void testDelegateJoinMessage() throws Exception {
    JsonNode arguments = null;
    MessageJson messageJson = new MessageJson("join", arguments);

    proxyController.delegateMessage(messageJson);
    String output = outputStream.toString();
    assertTrue(output.contains("smithisaladi"), "Expected 'smithisaladi' in the output");
  }

  @Test
  public void testDelegateEndGameMessage() throws Exception {
    JsonNode arguments = new ObjectNode(
        null);
    MessageJson messageJson = new MessageJson("end-game", arguments);

    proxyController.delegateMessage(messageJson);
    String output = outputStream.toString();
    assertTrue(output.contains("end-game"), "Expected 'end-game' in the output");
  }

  @Test
  public void testDelegateInvalidMessageName() throws Exception {
    JsonNode arguments = new ObjectNode(null);
    MessageJson invalidMessageJson = new MessageJson("invalid", arguments);
    assertThrows(IllegalStateException.class,
        () -> proxyController.delegateMessage(invalidMessageJson));
  }


  @Test
  public void testDelegateTakeShotsMessage() throws Exception {
    JsonNode arguments = new ObjectNode(
        null);
    MessageJson messageJson = new MessageJson("take-shots", arguments);

    proxyController.delegateMessage(messageJson);
    String output = outputStream.toString();
    assertTrue(output.contains("take-shots"), "Expected 'take-shots' in the output");
  }

  @Test
  public void testHandleReportDamage() throws Exception {
    JsonNode arguments = new ObjectNode(
        null);

    // Mock interaction for reportDamage
    List<Coord> hitList = new ArrayList<>();
    hitList.add(new Coord(1, 2));
    when(mockPlayer.reportDamage(any())).thenReturn(hitList);

    // Assuming the method is made accessible for testing
    proxyController.handleReportDamage(arguments);

    String output = outputStream.toString();
    assertFalse(output.contains("report-damage"),
        "Expected 'report-damage' in the output");
  }

  @Test
  public void testHandleSuccessfulHits() throws Exception {
    JsonNode arguments = new ObjectNode(
        null); // Here you should fill with actual JSON structure that is expected in arguments

    proxyController.handleSuccessfulHits(arguments);

    String output = outputStream.toString();
    assertTrue(output.contains("successful-hits"),
        "Expected 'successful-hits' in the output");
  }


  @Test
  public void testHandleSetup() throws Exception {
    // Given
    ObjectMapper objectMapper = new ObjectMapper();
    String setupArgumentsJson =
        "{\"width\": 10, \"height\": 10, \"fleet-spec\": {\"BATTLESHIP\": 1, "
            + "\"CARRIER\": 1, \"DESTROYER\": 1, \"SUBMARINE\": 1}}";
    JsonNode setupArguments = objectMapper.readTree(setupArgumentsJson);

    // When
    proxyController.handleSetup(setupArguments);

    // Then
    verify(mockPlayer).setup(anyInt(), anyInt(), any());
    assertFalse(outputStream.toString().contains("Sending RESPONSE"));
  }

  @Test
  public void testHandleSuccessfulHits1() throws Exception {
    // Given
    String sampleMessage = "{\"coordinates\": [{\"x\": 1, \"y\": 2}]}";
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode arguments = objectMapper.readTree(sampleMessage);

    // When
    proxyController.handleSuccessfulHits(arguments);

    // Then
    // Verify that successfulHits method of the player is called with appropriate arguments
    verify(mockPlayer).successfulHits(Arrays.asList(new Coord(1, 2)));
  }


  @Test
  public void testHandleReportDamage1() throws Exception {
    // Given
    String sampleMessage = "{\"coordinates\": [{\"x\": 1, \"y\": 2}]}";
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode arguments = objectMapper.readTree(sampleMessage);

    when(mockPlayer.reportDamage(any())).thenReturn(Arrays.asList(new Coord(3, 4)));

    // When
    proxyController.handleReportDamage(arguments);

    // Then
    verify(mockPlayer).reportDamage(Arrays.asList(new Coord(1, 2)));
  }

}