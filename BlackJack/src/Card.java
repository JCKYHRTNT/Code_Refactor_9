public class Card {
    public String face;
    public int value;

    public Card(String face, int value) {
        this.face = face;
        this.value = value;
    }

    @Override
    public String toString() {
        return face;
    }
}
