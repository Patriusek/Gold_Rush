package edu.io;

import edu.io.token.EmptyToken;
import edu.io.token.Token;

public class Board {
    public static record Coords(int col, int row) { }

    public final int size = 10;
    private Token[][] grid;

    public Board() {
        grid = new Token[size][size];
        clean();
    }

    public int size() {
        return size;
    }

    public Token peekToken(int x, int y) {
        return grid[y][x];
    }

    public void clean() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = new EmptyToken();
            }
        }
    }

    public void placeToken(int x, int y, Token token) {
        grid[y][x] = token;
    }

    public Coords getAvailableSquare() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] instanceof edu.io.token.EmptyToken) {
                    return new Coords(col, row);
                }
            }
        }
        throw new IllegalStateException("Board is full!");
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
