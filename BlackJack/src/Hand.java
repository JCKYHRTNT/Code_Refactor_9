import java.util.ArrayList;

public class Hand {
    public ArrayList<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getTotalValue() {
        int total = 0;
        for (Card card : cards) {
            total += card.value;
        }
        return total;
    }

    public void print(String owner) {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(owner + " Card " + (i + 1) + ": " + cards.get(i));
        }
        System.out.println(owner + " Total: " + getTotalValue());
    }
    
    public int compareTo(Hand other) {
    	return Integer.compare(this.getTotalValue(), other.getTotalValue());
    }
}
