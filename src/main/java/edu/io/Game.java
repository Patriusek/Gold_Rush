package edu.io;

import edu.io.player.NoTool;
import edu.io.player.Player;
import edu.io.token.*;

public class Game {
    private Board board;
    private Player player;

    public Game() {
        this.board = new Board();
        this.player = new Player();
    }

    public void join(Player player) {
        this.player = player;

        PlayerToken playerToken = new PlayerToken(player, board);
        player.assignToken(playerToken);
    }

    public void start() {
        PlayerToken playerToken = new PlayerToken(player, board);
        player.assignToken(playerToken);

        placeInitialObjects();

        board.display();
        System.out.println("Player gold: " + player.gold() + " oz");
    }

    private void placeInitialObjects() {
        board.clean();

        PlayerToken playerToken = player.token();

        board.placeToken(2, 3, new GoldToken(5.0));
        board.placeToken(4, 5, new GoldToken(2.5));
        board.placeToken(7, 8, new GoldToken(1.0));
        board.placeToken(1, 7, new GoldToken(3.0));
        board.placeToken(5, 2, new GoldToken(2.0));

        board.placeToken(3, 6, new PyriteToken());
        board.placeToken(8, 3, new PyriteToken());

        board.placeToken(6, 4, new PickaxeToken());
        board.placeToken(9, 9, new SluiceboxToken());
        board.placeToken(3, 9, new AnvilToken());

        board.placeToken(0, 0, playerToken);
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public void updateDisplay() {
        board.display();
        System.out.println("Gold: " + player.gold() + " oz");
        if (player.shed().getTool() instanceof NoTool) {
            System.out.println("Tool: None");
        } else {
            System.out.println("Tool: " + player.shed().getTool().getClass().getSimpleName());
            if (player.shed().getTool() instanceof PickaxeToken pickaxe) {
                System.out.println("  Durability: " + pickaxe.durability() + ", Gain Factor: " + pickaxe.gainFactor());
            } else if (player.shed().getTool() instanceof SluiceboxToken sluicebox) {
                System.out.println("  Durability: " + sluicebox.durability() + ", Gain Factor: " + sluicebox.gainFactor());
            }
        }
    }
}
