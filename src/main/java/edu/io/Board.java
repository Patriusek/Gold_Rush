package edu.io;

import edu.io.placement.IPlacementStrategy;
import edu.io.placement.LinearPlacementStrategy;
import edu.io.token.EmptyToken;
import edu.io.token.Token;

public class Board {
    public record Coords(int col, int row) { }

    public final int size = 10;
    private Token[][] grid;
    private IPlacementStrategy placementStrategy;

    public Board() {
        grid = new Token[size][size];
        this.placementStrategy = new LinearPlacementStrategy();
        clean();
    }

    public void setPlacementStrategy(IPlacementStrategy strategy) {
        this.placementStrategy = strategy;
    }

    public IPlacementStrategy getPlacementStrategy() {
        return this.placementStrategy;
    }

    public Coords getAvailableSquare() {
        return placementStrategy.getAvailableSquare(this);
    }

    public int size() {
        return size;
    }

    public void clean() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = new EmptyToken();
            }
        }
    }

    public void placeToken(int x, int y, Token token) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        grid[x][y] = token;
    }

    public Token peekToken(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        return grid[x][y];
    }

    public void display() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(grid[row][col].label() + " ");
            }
            System.out.println();
        }
    }

}
