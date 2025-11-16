package edu.io.token;

import edu.io.Board;

public class PlayerToken extends Token {
    private final Board board;
    private int col;
    private int row;

    public enum Move {
        LEFT,
        UP,
        RIGHT,
        DOWN,
        NONE
    }

    public PlayerToken(Board board) {
        super(Label.PLAYER_TOKEN_LABEL);

        this.board = board;

        this.col = 0;
        this.row = 0;

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

        board.placeToken(col, row, new EmptyToken());

        col = newCol;
        row = newRow;

        board.placeToken(col, row, this);
    }
}