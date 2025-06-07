public class SplitAction implements PlayerAction {
  @Override
  public Boolean execute(Game game, int turn, Card card1, Card card2) {
    System.out.println("you split");
    game.split(card1, card2);
    return false;
  }
}