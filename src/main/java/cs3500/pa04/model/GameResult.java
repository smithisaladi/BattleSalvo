package cs3500.pa04.model;

/**
 * An enumeration representing Game Results
 */
public enum GameResult {
  WIN("You win!"),
  LOSE("You Lose!"),
  DRAW("It's a Draw!"),
  IN_PROGRESS("");

  private final String reason;

  GameResult(String reason) {
    this.reason = reason;
  }

  public String getDescription() {
    return reason;
  }
}
