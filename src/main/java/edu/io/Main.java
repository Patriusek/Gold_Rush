package edu.io;

import edu.io.player.Player;
import edu.io.token.PlayerToken;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player player = new Player();
        game.join(player);
        game.start();

        PlayerToken playerToken = player.token();
        Scanner scanner = new Scanner(System.in);

        boolean playing = true;

        while (playing) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                continue;
            }

            char command = input.charAt(0);

            try {
                switch (command) {
                    case 'w' -> playerToken.move(PlayerToken.Move.UP);
                    case 'a' -> playerToken.move(PlayerToken.Move.LEFT);
                    case 's' -> playerToken.move(PlayerToken.Move.DOWN);
                    case 'd' -> playerToken.move(PlayerToken.Move.RIGHT);
                    case 'q' -> {
                        playing = false;
                        continue;
                    }
                    default -> {
                        continue;
                    }
                }

                game.updateDisplay();
            } catch (IllegalArgumentException e) {
                System.out.println("Cannot move there: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
