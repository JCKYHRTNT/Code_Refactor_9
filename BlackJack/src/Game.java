import java.util.*;

public class Game {
    private final Deck deck = new Deck();
    private final Scanner input = new Scanner(System.in);
    private final Hand playerHand = new Hand();
    private final Hand dealerHand = new Hand();
    private boolean running = true;

    public void start() {
        dealerStart();
        dealerCheck();
        System.out.println();
        System.out.println();
        System.out.println();
        playerTurn();
        System.out.println();
        System.out.println();
        if (running) {
            dealerTurn();
            dealerCheck();
            if (running) {
                win();
            }
        }
    }

    private void dealerStart() {
        Card card1 = Deck.draw();
        Card card2 = Deck.draw();

        dealerHand.addCard(card1);
        dealerHand.addCard(card2);

        System.out.println("Dealer Card 1: " + card1.face);
        System.out.println("Dealer Card 2: " + card2.face);
        System.out.println("Dealer Total: " + dealerHand.getTotalValue());
    }

    private void dealerTurn() {
        dealerHand.print("Dealer");

        while (dealerHand.getTotalValue() < 17) {
            System.out.println();
            System.out.println("Dealer Hits");
            Card newCard = Deck.draw();
            dealerHand.addCard(newCard);
            System.out.println("Dealer's New Card: " + newCard.face);
            System.out.println("Total: " + dealerHand.getTotalValue());
        }

        if (dealerHand.getTotalValue() >= 17) {
            System.out.println("Dealer Stands");
        }
    }

    private void playerTurn() {
        Card card1 = Deck.draw();
        Card card2 = Deck.draw();
        initialPlayerDraw(card1, card2);
        String choice;
        int turn = 0;

        while (running) {
            choice = getPlayerChoice(turn, card1, card2);
            Boolean result = handlePlayerChoice(choice, turn, card1, card2);
            if (result == null)
                continue;
            if (!result)
                break;
            turn++;
        }
    }

    private void initialPlayerDraw(Card card1, Card card2) {
        playerHand.addCard(card1);
        playerHand.addCard(card2);
        System.out.println("Player Card 1: " + card1.face);
        System.out.println("Player Card 2: " + card2.face);
        System.out.println("Player Total: " + playerHand.getTotalValue());
        playerCheck();
        System.out.println();
    }

    private String getPlayerChoice(int turn, Card card1, Card card2) {
        if (turn >= 1) {
            System.out.println("Would you like to hit or stand?");
        } else if (card1.face.equals(card2.face)) {
            System.out.println("What would you like to do: Hit, Stand, Double Down, or Split?");
        } else {
            System.out.println("What would you like to do: Hit, Stand, Double Down: ");
        }
        return input.nextLine();
    }

    private Boolean handlePlayerChoice(String choice, int turn, Card card1, Card card2) {
        PlayerAction action;

        if (choice.equalsIgnoreCase("hit")) {
            action = new HitAction();
        } else if (choice.equalsIgnoreCase("stand")) {
            action = new StandAction();
        } else if (choice.equalsIgnoreCase("double down")) {
            action = new DoubleDownAction();
        } else if (choice.equalsIgnoreCase("split")) {
            action = new SplitAction();
        } else {
            action = new InvalidAction();
        }

        return action.execute(this, turn, card1, card2);
    }

    public void hit() {
        Card newCard = Deck.draw();
        playerHand.addCard(newCard);
        System.out.println("Player's New Card: " + newCard.face);
        System.out.println("Total: " + playerHand.getTotalValue());
    }

    public void split(Card original1, Card original2) {
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();

        hand1.addCard(original1);
        hand1.addCard(Deck.draw());

        hand2.addCard(original2);
        hand2.addCard(Deck.draw());

        System.out.println("Hand 1:");
        System.out.println("Card 1: " + hand1.cards.get(0).face);
        System.out.println("Card 2: " + hand1.cards.get(1).face);
        System.out.println("Total: " + hand1.getTotalValue());

        System.out.println("Hand 2:");
        System.out.println("Card 1: " + hand2.cards.get(0).face);
        System.out.println("Card 2: " + hand2.cards.get(1).face);
        System.out.println("Total: " + hand2.getTotalValue());

        System.out.println();
        playSplitHand(hand1, 1);
        playSplitHand(hand2, 2);
    }

    private void playSplitHand(Hand hand, int handNumber) {
        String choice;
        int turn = 0;

        while (true) {
            if (turn == 0) {
                System.out.println("What would you like to do for hand " + handNumber + "? Hit, Stand, Double Down");
            } else {
                System.out.println("What would you like to do? hit or stand?");
            }
            choice = input.nextLine();

            if (choice.equalsIgnoreCase("stand")) {
                System.out.println("You stood");
                break;
            } else if (choice.equalsIgnoreCase("hit")) {
                turn++;
                System.out.println("you hit");
                Card newCard = Deck.draw();
                hand.addCard(newCard);
                System.out.println("Card " + (turn + 2) + ": " + newCard.face);
                System.out.println("Total: " + hand.getTotalValue());
            } else if (choice.equalsIgnoreCase("double down")) {
                System.out.println("you doubled down");
                Card newCard = Deck.draw();
                hand.addCard(newCard);
                System.out.println("Card 3: " + newCard.face);
                System.out.println("Total: " + hand.getTotalValue());
                break;
            } else {
                System.out.println("Thats not an option please try again.");
            }
        }
    }

    public void playerCheck() {
        checkBlackjackOrBust(playerHand, "Player");
    }

    private void dealerCheck() {
        checkBlackjackOrBust(dealerHand, "Dealer");
    }

    private void checkBlackjackOrBust(Hand hand, String owner) {
        int total = hand.getTotalValue();
        if (total == 21) {
            System.out.println("BLACK JACK!!!! " + owner + " Wins");
            running = false;
        } else if (total > 21) {
            System.out.println(owner + " Busted");
            running = false;
        }
    }

    private void win() {
        int player = playerHand.getTotalValue();
        int dealer = dealerHand.getTotalValue();

        if (dealer > player) {
            System.out.println("Dealer Wins");
        } else if (player > dealer) {
            System.out.println("Player Wins");
        } else {
            System.out.println("It's a tie no one wins");
        }
    }
}