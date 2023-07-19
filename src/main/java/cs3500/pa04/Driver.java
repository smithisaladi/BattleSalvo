package cs3500.pa04;

import cs3500.pa04.controller.BattleSalvoController;
import java.net.Socket;

/**
 * The driver class
 */
public class Driver {

  /**
   * Main method
   *
   * @param args arguments given to the main method
   */
  public static void main(String[] args) {

    try {
      if (args.length > 0) {
        String serverIp = args[0];
        int port = Integer.parseInt(args[1]);
        Socket socket = new Socket(serverIp, port);
        BattleSalvoController controller = new BattleSalvoController(socket);
      } else {
        BattleSalvoController controller = new BattleSalvoController(null);
        controller.playGame();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

