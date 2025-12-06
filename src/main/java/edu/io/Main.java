package edu.io;

import edu.io.token.PlayerToken;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gold Rush");

        Game game = new Game();
        Player player = new Player();
        game.join(player);
        game.start();

        PlayerToken playerToken = player.token();
        System.out.println("\nMoving player to collect gold...");

        try {
            playerToken.move(PlayerToken.Move.RIGHT);
            playerToken.move(PlayerToken.Move.RIGHT);
            playerToken.move(PlayerToken.Move.DOWN);
            playerToken.move(PlayerToken.Move.DOWN);
            playerToken.move(PlayerToken.Move.DOWN);
        } catch (IllegalArgumentException e) {
            System.out.println("Move error: " + e.getMessage());
        }

        System.out.println("Final gold amount: " + player.gold() + " oz");
    }
}
