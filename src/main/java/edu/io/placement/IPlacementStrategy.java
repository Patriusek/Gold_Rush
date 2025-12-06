package edu.io.placement;

import edu.io.Board;

public interface IPlacementStrategy {
    Board.Coords getAvailableSquare(Board board) throws IllegalStateException;
}
