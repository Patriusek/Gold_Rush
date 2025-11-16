package edu.io;

import edu.io.token.PlayerToken;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gold Rush");

        Board board = new Board();

        board.placeToken(1, 2, new PlayerToken(board));

        board.display();
    }
}
