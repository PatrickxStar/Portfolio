package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck implements DeckActions {

    private ArrayList<Card> myCards;
    private int numCards;

    public Deck() {
        myCards = new ArrayList<Card>();

        for (Suits suit : Suits.values()) {
            for (Values value : Values.values()) {
                myCards.add(new Card(suit, value));
            }
        }

        this.numCards = myCards.size();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(myCards, new Random());
    }

    @Override
    public Card dealNextCard() {
        Card topCard = myCards.remove(0);
        numCards--;
        return topCard;
    }

    @Override
    public void printDeck(int numToPrint) {
        for (int i = 0; i < numToPrint; i++) {
            System.out.println(myCards.get(i));
        }
    }
}
