package edu.io.placement;

import edu.io.Board;

public class LinearPlacementStrategy implements IPlacementStrategy {
    @Override
    public Board.Coords getAvailableSquare(Board board) throws IllegalStateException {
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.size(); col++) {
                if (board.peekToken(col, row) instanceof edu.io.token.EmptyToken) {
                    return new Board.Coords(col, row);
                }
            }
        }
        throw new IllegalStateException("Board is full!");
    }
}