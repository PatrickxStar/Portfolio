package org.example;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;

    public Player() {
        this.hand = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void printHand() {
        for (Card card : hand) {
            System.out.println(card);
        }
    }

    public void printInitialHand() {
        System.out.println(hand.get(0) + " and [Hidden]");
    }

    public int calculateHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : hand) {
            value += card.getValue().getValue();
            if (card.getValue() == Values.ACE) {
                numAces++;
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }

    public boolean hasBlackjack() {
        return hand.size() == 2 && calculateHandValue() == 21;
    }

    public void clearHand() {
        hand.clear();
    }
}
