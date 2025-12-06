package edu.io;

import edu.io.token.GoldToken;
import edu.io.token.PlayerToken;
import edu.io.token.PyriteToken;

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
        board.placeToken(2, 3, new GoldToken(5.0));
        board.placeToken(4, 5, new GoldToken(2.5));
        board.placeToken(7, 8, new PyriteToken());

        board.display();
        System.out.println("Player gold: " + player.gold() + " oz");
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }
}
