package org.example;

public class Card {
    private Suits suit;
    private Values value;

    public Card(Suits suit, Values value) {
        this.suit = suit;
        this.value = value;
    }

    public Suits getSuit() {
        return suit;
    }

    public Values getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
