package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.enumerations.GameResult;
import cs3500.pa04.adapter.ShipAdapter;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.data.EmptyJson;
import cs3500.pa04.json.data.FleetJson;
import cs3500.pa04.json.data.ShipJson;
import cs3500.pa04.json.request.EndGameRequestJson;
import cs3500.pa04.json.request.SetupRequestJson;
import cs3500.pa04.json.response.JoinResponse;
import cs3500.pa04.json.response.MessageJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * The responsibility of this class is to listen for
 * server inputs and to delegate the message to the proper
 * methods.
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;

  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Constructs a new ProxyController with a connected server socket and a player.
   *
   * @param server The server socket to connect to.
   * @param player The player who will be using this ProxyController.
   * @throws IOException If an I/O error occurs when creating the input and output streams.
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }


  /**
   * Listens for the server input
   */
  public void listen() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      this.out.println(":(");
    }
  }

  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    switch (name) {
      case "join" -> join();
      case "setup" -> setup(arguments);
      case "take-shots" -> takeShots();
      case "report-damage" -> reportDamage(arguments);
      case "successful-hits" -> successfulHits(arguments);
      case "end-game" -> endGame(arguments);
      default -> this.out.println("Invalid message.");
    }
  }

  private void join() {
    JoinResponse joinResponse = new JoinResponse("pa04-almostthreegoodatcoding", "SINGLE");
    JsonNode joinResponseNodeJson = this.mapper.convertValue(joinResponse, JsonNode.class);
    MessageJson temp = new MessageJson("join", joinResponseNodeJson);

    JsonNode response = this.mapper.convertValue(temp, JsonNode.class);

    this.out.println(response);
  }

  //decompose and separate concerns
  private void setup(JsonNode setupArgs) {
    SetupRequestJson setupInfo = this.mapper.convertValue(setupArgs, SetupRequestJson.class);

    //
    //process request into a response : in this case we respond with a fleet
    //
    List<Ship> ships = this.player.setup(
        setupInfo.height(), setupInfo.width(), setupInfo.specs().getSpecifications());

    //
    //serialize response into JSON (we can defer this to model)
    //
    ShipJson[] shipsJson = new ShipJson[ships.size()];
    for (int i = 0; i < ships.size(); i++) {
      shipsJson[i] = ShipAdapter.shipToJson(ships.get(i));
    }
    FleetJson fleet = new FleetJson(shipsJson);
    JsonNode fleetJsonNode = this.mapper.convertValue(fleet, JsonNode.class);
    MessageJson temp = new MessageJson("setup", fleetJsonNode);
    JsonNode response = this.mapper.convertValue(temp, JsonNode.class);

    //
    //send back the response
    //
    this.out.print(response);
  }

  private void takeShots() {
    List<Coord> listOfShots = this.player.takeShots(); //retrieve
    JsonNode response = JsonUtils.serializeCoordList("take-shots", listOfShots); //response

    this.out.print(response); //send
  }

  private void reportDamage(JsonNode damageArgs) {
    List<Coord> enemyShots = JsonUtils.deserializeCoordList(damageArgs); //parse
    List<Coord> damage = this.player.reportDamage(enemyShots); //retrieve
    JsonNode response = JsonUtils.serializeCoordList("report-damage", damage); //response
    this.out.print(response); //send
  }

  private void successfulHits(JsonNode successArgs) {
    List<Coord> successfulShots = JsonUtils.deserializeCoordList(successArgs);
    this.player.successfulHits(successfulShots);
    JsonNode args = this.mapper.convertValue(new EmptyJson(), JsonNode.class);
    MessageJson msg = new MessageJson("successful-hits", args);
    JsonNode response = this.mapper.convertValue(msg, JsonNode.class);
    this.out.print(response);
  }

  private void endGame(JsonNode endGameArgs) {
    EndGameRequestJson endGameJson =
        this.mapper.convertValue(endGameArgs, EndGameRequestJson.class);
    GameResult result = switch (endGameJson.result()) {
      case "WIN" -> GameResult.WIN;
      case "LOSE" -> GameResult.LOSS;
      default -> GameResult.DRAW;
    };

    this.player.endGame(result, endGameJson.reason());

    JsonNode args = this.mapper.convertValue(new EmptyJson(), JsonNode.class);
    MessageJson msg = new MessageJson("end-game", args);
    JsonNode response = this.mapper.convertValue(msg, JsonNode.class);
    this.out.print(response);
  }
}
