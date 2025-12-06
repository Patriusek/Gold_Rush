package edu.io.token;

import edu.io.Board;
import edu.io.Player;

public class PlayerToken extends Token {
    private final Board board;
    private int col;
    private int row;
    private Player player;

    public enum Move {
        LEFT,
        UP,
        RIGHT,
        DOWN,
        NONE
    }

    public PlayerToken(Player player, Board board) {
        super(Label.PLAYER_TOKEN_LABEL);
        this.player = player;
        this.board = board;

        Board.Coords startPos = board.getAvailableSquare();
        this.col = startPos.col();
        this.row = startPos.row();

        board.placeToken(col, row, this);
    }

    public Board.Coords pos() {
        return new Board.Coords(col, row);
    }

    public void move(Move move) {
        int newCol = col;
        int newRow = row;

        switch (move) {
            case LEFT:
                newCol--;
                break;
            case UP:
                newRow--;
                break;
            case RIGHT:
                newCol++;
                break;
            case DOWN:
                newRow++;
                break;
            case NONE:
                return;
            default:
                return;
        }

        if (newCol < 0 || newCol >= board.size() || newRow < 0 || newRow >= board.size()) {
            throw new IllegalArgumentException("player is outside");
        }

        Token tokenAtNewPosition = board.peekToken(newCol, newRow);

        if (player != null) {
            player.interactWithToken(tokenAtNewPosition);
        }

        board.placeToken(col, row, new EmptyToken());

        col = newCol;
        row = newRow;

        board.placeToken(col, row, this);
        board.display();
    }
}