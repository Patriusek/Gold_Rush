import edu.io.Board;
import edu.io.Player;
import edu.io.placement.IPlacementStrategy;
import edu.io.placement.LinearPlacementStrategy;
import edu.io.placement.RandomPlacementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlacementStrategyTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    public void testDefaultStrategyIsLinear() {
        Board board = new Board();
        assertTrue(board.getPlacementStrategy() instanceof LinearPlacementStrategy);
    }

    @Test
    public void testSetRandomStrategy() {
        Board board = new Board();
        board.setPlacementStrategy(new RandomPlacementStrategy());
        assertTrue(board.getPlacementStrategy() instanceof RandomPlacementStrategy);
    }

    @Test
    public void testLinearStrategyFindsEmptySquare() {
        Board board = new Board();
        IPlacementStrategy strategy = new LinearPlacementStrategy();

        Board.Coords coords = strategy.getAvailableSquare(board);
        assertEquals(0, coords.col());
        assertEquals(0, coords.row());

        board.placeToken(0, 0, new edu.io.token.GoldToken());
        coords = strategy.getAvailableSquare(board);
        assertEquals(1, coords.col());
        assertEquals(0, coords.row());
    }

    @Test
    public void testRandomStrategyFindsEmptySquare() {
        Board board = new Board();
        IPlacementStrategy strategy = new RandomPlacementStrategy();

        Board.Coords coords = strategy.getAvailableSquare(board);
        assertNotNull(coords);
        assertTrue(coords.col() >= 0 && coords.col() < board.size());
        assertTrue(coords.row() >= 0 && coords.row() < board.size());
    }

    @Test
    public void testFullBoardThrowsException() {
        Board board = new Board();
        IPlacementStrategy strategy = new LinearPlacementStrategy();

        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.size(); col++) {
                board.placeToken(col, row, new edu.io.token.GoldToken());
            }
        }

        assertThrows(IllegalStateException.class, () -> {
            strategy.getAvailableSquare(board);
        });
    }

    @Test
    public void testPlayerTokenUsesStrategy() {
        Board board = new Board();

        board.setPlacementStrategy(new LinearPlacementStrategy());
        edu.io.token.PlayerToken token1 = new edu.io.token.PlayerToken(player, board);
        assertEquals(0, token1.pos().col());
        assertEquals(0, token1.pos().row());

        board.clean();
        board.setPlacementStrategy(new RandomPlacementStrategy());
        edu.io.token.PlayerToken token2 = new edu.io.token.PlayerToken(player, board);
        assertNotNull(token2.pos());
    }
}
