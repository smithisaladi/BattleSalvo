package cs3500.pa04.controller;

import cs3500.pa04.model.BattleSalvoPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.fleet.ShipType;
import cs3500.pa04.view.BattleSalvoView;
import java.net.Socket;
import java.util.List;
import java.util.Map;


/**
 * The controller class of the BattleSalvoGame
 */
public class BattleSalvoController {

  private BattleSalvoView view;
  private BattleSalvoPlayer humanPlayer = null;
  private BattleSalvoPlayer aiPlayer = null;
  private ProxyController proxyPlayer = null;

  /**
   * The main constructor
   *
   * @param server server socket
   * @throws Exception if an exception occurs
   */
  public BattleSalvoController(Socket server) throws Exception {
    this.view = new BattleSalvoView();
    this.humanPlayer = new BattleSalvoPlayer();
    this.aiPlayer = new BattleSalvoPlayer();

    if (server != null) {
      proxyPlayer = new ProxyController(server, this.humanPlayer);
    }
  }

  /**
   * Starts the game of BattleSalvo
   *
   * @throws Exception if an exception occurs
   */
  public void playGame() throws Exception {
    view.displayWelcomeMessage();
    int[] boardSize = view.getBoardSizeInput();
    Map<ShipType, Integer> fleetSizes = view.getFleetSizeInput(boardSize);

    humanPlayer.setup(boardSize[0], boardSize[1], fleetSizes);
    aiPlayer.setup(boardSize[0], boardSize[1], fleetSizes);

    humanPlayer.printBoard();

    GameResult result = GameResult.IN_PROGRESS;
    while (result == GameResult.IN_PROGRESS) {

      List<Coord> humanPlayerShots = view.getShotsInput(humanPlayer.getMyBoard());
      //List<Coord> humanPlayerShots = humanPlayer.takeShots();
      humanPlayer.trackHumanShots(humanPlayerShots);
      List<Coord> aiPlayerShots = aiPlayer.takeShots();

      List<Coord> humanPlayerHits = aiPlayer.reportDamage(humanPlayerShots);
      List<Coord> aiPlayerHits = humanPlayer.reportDamage(aiPlayerShots);

      // Update successful hits for both players
      humanPlayer.successfulHits(humanPlayerHits);
      aiPlayer.successfulHits(aiPlayerHits);

      view.displayShotStatistics(
          humanPlayerHits.size(),
          humanPlayerShots.size() - humanPlayerHits.size(),
          aiPlayerHits.size(),
          aiPlayerShots.size() - aiPlayerHits.size());


      humanPlayer.printBoard();

      if (humanPlayer.getMyBoard().getFleet().getNumOfShipsFloating() == 0
          && aiPlayer.getMyBoard().getFleet().getNumOfShipsFloating() == 0) {
        result = GameResult.DRAW;
      } else if (humanPlayer.getMyBoard().getFleet().getNumOfShipsFloating() == 0) {
        result = GameResult.LOSE;
      } else if (aiPlayer.getMyBoard().getFleet().getNumOfShipsFloating() == 0) {
        result = GameResult.WIN;
      }
    }

    humanPlayer.endGame(result, result.getDescription());
  }
}