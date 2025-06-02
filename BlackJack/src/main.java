import java.util.*;

public class Main {

	public static Deck deck = new Deck();
	public static Scanner input = new Scanner(System.in);
	public static Hand playerHand = new Hand();
	public static Hand dealerHand = new Hand();
	public static boolean running = true;

	public static void main(String[] args) {
		dealerStart();
		dealerCheck();
		System.out.println();
		System.out.println();
		System.out.println();
		playerTurn();
		System.out.println();
		System.out.println();
		if (running == true) {
			dealerTurn();
			dealerCheck();
			if (running == true) {
				win();
			}
		}
	}

	public static void playerTurn() {
		Card card1 = deck.draw();
		Card card2 = deck.draw();
		initialPlayerDraw(card1, card2);
		String choice = " ";
		int turn = 0;

		while (running) {
			choice = getPlayerChoice(turn, card1, card2);
			Boolean result = handlePlayerChoice(choice, turn, card1, card2);
			if (result == null) continue;
			if (!result) break;
			turn++;
		}
	}

	public static void initialPlayerDraw(Card card1, Card card2) {
		playerHand.addCard(card1);
		playerHand.addCard(card2);
		System.out.println("Player Card 1: " + card1.face);
		System.out.println("Player Card 2: " + card2.face);
		System.out.println("Player Total: " + playerHand.getTotalValue());
		playerCheck();
		System.out.println();
	}

	public static String getPlayerChoice(int turn, Card card1, Card card2) {
		if (turn >= 1) {
			System.out.println("Would you like to hit or stand?");
		} else if (card1.face.equals(card2.face)) {
			System.out.println("What would you like to do: Hit, Stand, Double Down, or Split?");
		} else {
			System.out.println("What would you like to do: Hit, Stand, Double Down: ");
		}
		return input.nextLine();
	}

	public static Boolean handlePlayerChoice(String choice, int turn, Card card1, Card card2) {
		if (choice.equalsIgnoreCase("hit")) {
			System.out.println("you hit");
			hit();
			playerCheck();
			return true;
		} else if (choice.equalsIgnoreCase("stand")) {
			System.out.println("you stood");
			return false;
		} else if (choice.equalsIgnoreCase("double down")) {
			System.out.println("you doubled down");
			hit();
			playerCheck();
			return false;
		} else if (choice.equalsIgnoreCase("split")) {
			System.out.println("you split");
			split(card1, card2);
			return false;
		} else {
			System.out.println("Sorry that wasnt an option please try again");
			return null;
		}
	}

	public static void dealerStart() {
		Card card1 = Deck.draw();
		Card card2 = Deck.draw();

		dealerHand.addCard(card1);
		dealerHand.addCard(card2);

		System.out.println("Dealer Card 1: " + card1.face);
		System.out.println("Dealer Card 2: " + card2.face);
		System.out.println("Dealer Total: " + dealerHand.getTotalValue());
	}

	public static void dealerTurn() {
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

	public static void hit() {
		Card newCard = Deck.draw();
		playerHand.addCard(newCard);
		System.out.println("Player's New Card: " + newCard.face);
		System.out.println("Total: " + playerHand.getTotalValue());
	}

	public static void split(Card original1, Card original2) {
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

	public static void playSplitHand(Hand hand, int handNumber) {
		String choice = "";
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

	public static void playerCheck() {
		int total = playerHand.getTotalValue();
		if (total == 21) {
			System.out.println("BLACK JACK!!!! Player Wins");
			running = false;
		} else if (total > 21) {
			System.out.println("Player Busted");
			running = false;
		}
	}

	public static void dealerCheck() {
		int total = dealerHand.getTotalValue();
		if (total == 21) {
			System.out.println("BLACK JACK!!!! Dealer Wins");
			running = false;
		} else if (total > 21) {
			System.out.println("Dealer Busted");
			running = false;
		}
	}

	public static void win() {
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
