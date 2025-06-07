public interface PlayerAction {
  Boolean execute(Game game, int turn, Card card1, Card card2);
}