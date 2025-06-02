import java.util.Random;

public class Deck {
    public static final String[] faces = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"
    };

    public static final int[] values = {
        2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11
    };

    public static Random rand = new Random();

    public static Card draw() {
        int index = rand.nextInt(13);
        return new Card(faces[index], values[index]);
    }
}
