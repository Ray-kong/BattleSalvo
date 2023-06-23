package cs3500.pa04.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.ComputerPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumerations.ShipType;
import cs3500.pa03.singleton.Randomizer;
import cs3500.pa03.view.ConsoleView;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.data.EmptyJson;
import cs3500.pa04.json.data.FleetSpecJson;
import cs3500.pa04.json.request.EndGameRequestJson;
import cs3500.pa04.json.request.SetupRequestJson;
import cs3500.pa04.json.response.MessageJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private final ObjectMapper mapper = new ObjectMapper();

  private Randomizer random;

  private ComputerPlayer player;
  private Map<ShipType, Integer> fleet;

  @BeforeEach
  void setUp() {
    this.testLog = new ByteArrayOutputStream();
    this.random = Randomizer.getRandomizer();
    this.random.updateSeed(100);
    assertEquals("", logToString());
    player = new ComputerPlayer();
    fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);
  }

  @AfterEach
  void tearDown() {
    this.random.updateSeed();
  }

  @Test
  void listenBadRequest() {
    //Create a sample server request
    JsonNode args = this.mapper.convertValue(new EmptyJson(), JsonNode.class);
    MessageJson msgRequest = new MessageJson("invalid", args);
    JsonNode request = this.mapper.convertValue(msgRequest, JsonNode.class);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "Invalid message.\n:(\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that join is sending back the correct message to the server.
   */
  @Test
  public void join() {
    //Create a sample server request
    JsonNode args = this.mapper.convertValue(new EmptyJson(), JsonNode.class);
    MessageJson msgRequest = new MessageJson("join", args);
    JsonNode request = this.mapper.convertValue(msgRequest, JsonNode.class);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":"
        + "\"pa04-almostthreegoodatcoding\","
        + "\"game-type\":\"SINGLE\"}}\n:(\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that setup is sending back the correct message to the server.
   */
  @Test
  void setup() {
    FleetSpecJson fleetSpec = new FleetSpecJson(1, 1, 1, 1);
    SetupRequestJson setupRequest = new SetupRequestJson(10, 10, fleetSpec);
    JsonNode setupJson = this.mapper.convertValue(setupRequest, JsonNode.class);
    MessageJson msg = new MessageJson("setup", setupJson);
    JsonNode request = this.mapper.convertValue(msg, JsonNode.class);
    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":6,"
        + "\"y\":2},\"length\":6,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":3,\"y\":5},\"length"
        + "\":5,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":7,\"y\":5},\"length\":4,\"direction"
        + "\":\"VERTICAL\"},{\"coord\":{\"x\":6,\"y\":9},\"length\":3,\"direction\":\"HORIZONTAL\""
        + "}]}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Check that takeShots is sending back the correct message to the server.
   */
  @Test
  void takeShots() {
    this.player.setup(6, 6, fleet);

    JsonNode args = this.mapper.convertValue(new EmptyJson(), JsonNode.class);
    MessageJson msg = new MessageJson("take-shots", args);
    JsonNode request = this.mapper.convertValue(msg, JsonNode.class);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\""
        + ":0,\"y\":0},{\"x\":3,\"y\":0},{\"x\":1,\"y\":1},{\"x\":4,\"y\":1}]}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Check that reportDamage is sending back the correct message to the server.
   */
  @Test
  void reportDamage() {
    this.player.setup(6, 6, fleet);
    List<Coord> shots = new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(3, 3)));
    JsonNode request = JsonUtils.serializeCoordList("report-damage", shots);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[{"
        + "\"x\":0,\"y\":0},{\"x\":3,\"y\":3}]}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Check that successfulHits is sending back the correct message to the server.
   */
  @Test
  void successfulHits() {
    List<Coord> list = new ArrayList<>();
    JsonNode request = JsonUtils.serializeCoordList("successful-hits", list);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }
    //TODO: board might be null?
    this.controller.listen();

    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":{}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Check that endGame is sending back the correct message to the server.
   */
  @Test
  void endGameWin() {
    EndGameRequestJson egrJson = new EndGameRequestJson("WIN", "You won!");
    JsonNode args = this.mapper.convertValue(egrJson, JsonNode.class);
    MessageJson msg = new MessageJson("end-game", args);
    JsonNode request = this.mapper.convertValue(msg, JsonNode.class);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Check that endGame is sending back the correct message to the server.
   */
  @Test
  void endGameLose() {
    EndGameRequestJson egrJson = new EndGameRequestJson("LOSE", "You lost!");
    JsonNode args = this.mapper.convertValue(egrJson, JsonNode.class);
    MessageJson msg = new MessageJson("end-game", args);
    JsonNode request = this.mapper.convertValue(msg, JsonNode.class);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Check that endGame is sending back the correct message to the server.
   */
  @Test
  void endGameDraw() {
    EndGameRequestJson egrJson = new EndGameRequestJson("DRAW", "Rip bozo!");
    JsonNode args = this.mapper.convertValue(egrJson, JsonNode.class);
    MessageJson msg = new MessageJson("end-game", args);
    JsonNode request = this.mapper.convertValue(msg, JsonNode.class);

    Mocked socket = new Mocked(this.testLog, List.of(request.toString()));

    try {
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail();
    }

    this.controller.listen();

    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}:(\n";

    assertEquals(expected, logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }
}