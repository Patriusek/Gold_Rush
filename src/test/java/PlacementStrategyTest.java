import edu.io.Board;
import edu.io.player.Player;
import edu.io.placement.IPlacementStrategy;
import edu.io.placement.LinearPlacementStrategy;
import edu.io.placement.RandomPlacementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlacementStrategyTest {
    Player player;
    Board board;

    @BeforeEach
    void setUp() {
        player = new Player();
        board = new Board();
    }

    @Test
    public void default_strategy_check_is_linear() {
        assertInstanceOf(LinearPlacementStrategy.class, board.getPlacementStrategy());
    }

    @Test
    public void set_random_strategy_should_be_random() {
        board.setPlacementStrategy(new RandomPlacementStrategy());
        assertInstanceOf(RandomPlacementStrategy.class, board.getPlacementStrategy());
    }

    @Test
    public void linear_strategy_getAvailableSquare_finds_first_empty_square() {
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
    public void random_strategy_should_find_empty_square() {
        IPlacementStrategy strategy = new RandomPlacementStrategy();

        Board.Coords coords = strategy.getAvailableSquare(board);
        assertNotNull(coords);
        assertTrue(coords.col() >= 0 && coords.col() < board.size());
        assertTrue(coords.row() >= 0 && coords.row() < board.size());
    }

    @Test
    public void linear_strategy_full_board_should_throw_exception() {
        IPlacementStrategy strategy = new LinearPlacementStrategy();

        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.size(); col++) {
                board.placeToken(col, row, new edu.io.token.GoldToken());
            }
        }

        assertThrows(IllegalStateException.class, () -> strategy.getAvailableSquare(board));
    }

    @Test
    public void random_strategy_full_board_should_throw_exception() {
        IPlacementStrategy strategy = new RandomPlacementStrategy();

        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.size(); col++) {
                board.placeToken(col, row, new edu.io.token.GoldToken());
            }
        }

        assertThrows(IllegalStateException.class, () -> strategy.getAvailableSquare(board));
    }
}
