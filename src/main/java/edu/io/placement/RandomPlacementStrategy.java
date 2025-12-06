package edu.io.placement;

import edu.io.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlacementStrategy implements IPlacementStrategy {
    private Random random = new Random();

    @Override
    public Board.Coords getAvailableSquare(Board board) throws IllegalStateException {
        List<Board.Coords> emptySquares = new ArrayList<>();

        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.size(); col++) {
                if (board.peekToken(col, row) instanceof edu.io.token.EmptyToken) {
                    emptySquares.add(new Board.Coords(col, row));
                }
            }
        }

        if (emptySquares.isEmpty()) {
            throw new IllegalStateException("Board is full!");
        }

        return emptySquares.get(random.nextInt(emptySquares.size()));
    }
}
