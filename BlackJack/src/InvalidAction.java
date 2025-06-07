public class InvalidAction implements PlayerAction {
  @Override
  public Boolean execute(Game game, int turn, Card card1, Card card2) {
    System.out.println("Sorry that wasn't an option please try again");
    return null;
  }
}