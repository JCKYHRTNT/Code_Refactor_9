public class DoubleDownAction implements PlayerAction {
  @Override
  public Boolean execute(Game game, int turn, Card card1, Card card2) {
    System.out.println("you doubled down");
    game.hit();
    game.playerCheck();
    return false;
  }
}