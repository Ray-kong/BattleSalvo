package cs3500.pa04;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.ComputerPlayer;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.net.Socket;

/**
 * Driver of the program (PA04).
 */
public class Driver {
  //TODO: if 0 args are accepted

  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   */
  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    ProxyController proxyController = new ProxyController(server, new ComputerPlayer());
    proxyController.listen();
  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    if(args.length == 0) {
      GameController c = new GameController();
      c.initializeGame();
    }
    else {
      String host = args[0];
      int port;
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(e);
      }
      try {
        Driver.runClient(host, port);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
