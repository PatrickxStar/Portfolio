package org.example;

import java.util.Scanner;

public class GameRunner {

    public static void main(String[] args) {
        // Play some music
        String filepath = "/CasinoJazz.wav";
        PlayMusic music = new PlayMusic();
        music.playMusic(filepath);

        Scanner sc = new Scanner(System.in);

        // Create and shuffle the deck
        Deck deck = new Deck();
        deck.shuffle();

        // Initialize player and dealer
        Player player = new Player();
        Player dealer = new Player();

        // Player's initial balance
        int playerBalance = 1000000;
        int playerBet;

        System.out.println("Welcome to Blackjack!");

        // Game loop
        while (true) {
            System.out.println("Your current balance: $" + playerBalance);
            System.out.print("Enter your bet (in increments of 5): ");
            playerBet = getValidBet(sc, playerBalance);

            // Initial dealing
            player.addCard(deck.dealNextCard());
            dealer.addCard(deck.dealNextCard());
            player.addCard(deck.dealNextCard());
            dealer.addCard(deck.dealNextCard());

            // Display initial hands
            System.out.println("Player's hand: ");
            player.printHand();
            System.out.println("Dealer's hand: ");
            dealer.printInitialHand();

            // Player's turn
            boolean playerBusted = false;
            while (true) {
                System.out.print("Do you want to hit or stand? (h/s): ");
                char action = sc.next().charAt(0);
                if (action == 'h') {
                    player.addCard(deck.dealNextCard());
                    System.out.println("Player's hand: ");
                    player.printHand();
                    if (player.calculateHandValue() > 21) {
                        System.out.println("You busted!");
                        playerBusted = true;
                        break;
                    }
                } else if (action == 's') {
                    break;
                }
            }

            // Dealer's turn
            while (dealer.calculateHandValue() < 17) {
                dealer.addCard(deck.dealNextCard());
            }

            System.out.println("Dealer's hand: ");
            dealer.printHand();

            // Determine winner
            if (playerBusted) {
                System.out.println("Dealer wins!");
                playerBalance -= playerBet;
            } else if (dealer.calculateHandValue() > 21 || player.calculateHandValue() > dealer.calculateHandValue()) {
                System.out.println("Player wins!");
                playerBalance += playerBet;
            } else if (player.calculateHandValue() < dealer.calculateHandValue()) {
                System.out.println("Dealer wins!");
                playerBalance -= playerBet;
            } else {
                System.out.println("It's a tie!");
            }

            // Check if the player wants to continue
            System.out.print("Do you want to play again? (y/n): ");
            char playAgain = sc.next().charAt(0);
            if (playAgain != 'y') {
                break;
            }

            // Reset hands
            player.clearHand();
            dealer.clearHand();
        }

        System.out.println("Thank you for playing! Your final balance is $" + playerBalance);
        sc.close();
    }

    private static int getValidBet(Scanner sc, int playerBalance) {
        int playerBet;
        while (true) {
            try {
                playerBet = Integer.parseInt(sc.next());
                if (playerBet > playerBalance) {
                    System.out.println("You cannot bet more than your balance.");
                } else if (playerBet % 5 != 0) {
                    System.out.println("Please enter a bet in increments of 5.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return playerBet;
    }
}
