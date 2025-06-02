import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random rand = new Random();
    static Scanner input = new Scanner(System.in);
    static boolean running = true;

    static Deck deck = new Deck();
    static Card dealerCard1;
    static Card dealerCard2;
    static int dealerCardVal;

    static Card playerCard1;
    static Card playerCard2;
    static int playerCardVal;

    public static void main(String[] args) {
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

    public static void playerTurn() {
        playerCard1 = deck.drawCard();
        playerCard2 = deck.drawCard();
        playerCardVal = playerCard1.getValue() + playerCard2.getValue();

        System.out.println("Player Card 1: " + playerCard1);
        System.out.println("Player Card 2: " + playerCard2);
        System.out.println("Player Total: " + playerCardVal);
        playerCheck();
        System.out.println();

        String choice;
        int turn = 0;

        while (running) {
            if (turn >= 1) {
                System.out.println("Would you like to hit or stand?");
            } else if (playerCard1.getRank().equals(playerCard2.getRank())) {
                System.out.println("What would you like to do: Hit, Stand, Double Down, or Split?");
            } else {
                System.out.println("What would you like to do: Hit, Stand, Double Down:");
            }

            choice = input.nextLine().toLowerCase();

            switch (choice) {
                case "hit":
                    System.out.println("you hit");
                    turn++;
                    hit();
                    playerCheck();
                    break;
                case "stand":
                    System.out.println("you stood");
                    return;
                case "double down":
                    System.out.println("you doubled down");
                    hit();
                    playerCheck();
                    return;
                case "split":
                    if (playerCard1.getRank().equals(playerCard2.getRank())) {
                        System.out.println("you split");
                        split();
                    } else {
                        System.out.println("Split not allowed. Cards are not identical.");
                    }
                    return;
                default:
                    System.out.println("Sorry that wasnt an option please try again");
            }
        }
    }

    public static void dealerTurn() {
        System.out.println("Dealer Card 1: " + dealerCard1);
        System.out.println("Dealer Card 2: " + dealerCard2);
        System.out.println("Dealer Total: " + dealerCardVal);

        while (dealerCardVal < 17) {
            System.out.println();
            System.out.println("Dealer Hits");
            Card card = deck.drawCard();
            dealerCardVal += card.getValue();
            System.out.println("Dealer's New Card: " + card);
            System.out.println("Total: " + dealerCardVal);
        }

        if (dealerCardVal >= 17) {
            System.out.println("Dealer Stands");
        }
    }

    public static void dealerStart() {
        dealerCard1 = deck.drawCard();
        dealerCard2 = deck.drawCard();
        dealerCardVal = dealerCard1.getValue() + dealerCard2.getValue();

        System.out.println("Dealer Card 1: " + dealerCard1);
        System.out.println("Dealer Card 2: " + dealerCard2);
        System.out.println("Dealer Total: " + dealerCardVal);
    }

    public static void hit() {
        Card card = deck.drawCard();
        playerCardVal += card.getValue();
        System.out.println("Player's New Card: " + card);
        System.out.println("Total: " + playerCardVal);
    }

    public static void split() {
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        hand1.addCard(playerCard1);
        hand2.addCard(playerCard2);
        hand1.addCard(deck.drawCard());
        hand2.addCard(deck.drawCard());

        System.out.println("Hand 1:");
        hand1.print("Player");
        handleSplitTurn(hand1);

        System.out.println("Hand 2:");
        hand2.print("Player");
        handleSplitTurn(hand2);
    }

    public static void handleSplitTurn(Hand hand) {
        String choice;
        int turn = 0;

        while (true) {
            if (turn == 0) {
                System.out.println("What would you like to do? Hit, Stand, Double Down");
            } else {
                System.out.println("What would you like to do? hit or stand?");
            }

            choice = input.nextLine().toLowerCase();

            switch (choice) {
                case "stand":
                    System.out.println("You stood");
                    return;
                case "hit":
                    turn++;
                    System.out.println("you hit");
                    Card card = deck.drawCard();
                    hand.addCard(card);
                    System.out.println("Card " + (turn + 2) + ": " + card);
                    System.out.println("Total: " + hand.getTotalValue());
                    break;
                case "double down":
                    System.out.println("you doubled down");
                    Card card2 = deck.drawCard();
                    hand.addCard(card2);
                    System.out.println("Card 3: " + card2);
                    System.out.println("Total: " + hand.getTotalValue());
                    return;
                default:
                    System.out.println("Thats not an option please try again.");
            }
        }
    }

    public static void playerCheck() {
        if (playerCardVal == 21) {
            System.out.println("BLACK JACK!!!! Player Wins");
            running = false;
        } else if (playerCardVal > 21) {
            System.out.println("Player Busted");
            running = false;
        }
    }

    public static void dealerCheck() {
        if (dealerCardVal == 21) {
            System.out.println("BLACK JACK!!!! Dealer Wins");
            running = false;
        } else if (dealerCardVal > 21) {
            System.out.println("Dealer Busted");
            running = false;
        }
    }

    public static void win() {
        if (dealerCardVal > playerCardVal) {
            System.out.println("Dealer Wins");
        } else if (playerCardVal > dealerCardVal) {
            System.out.println("Player Wins");
        } else {
            System.out.println("It's a tie no one wins");
        }
    }
}