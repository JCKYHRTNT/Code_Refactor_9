public class StandAction implements PlayerAction {
  @Override
  public Boolean execute(Game game, int turn, Card card1, Card card2) {
    System.out.println("you stood");
    return false;
  }
}