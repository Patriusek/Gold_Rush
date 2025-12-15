import edu.io.player.Player;
import edu.io.token.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SluiceboxTest {
    @Test
    void sluicebox_gain_when_player_interacts_with_gold() {
        var player = new Player();
        player.interactWithToken(new SluiceboxToken());
        player.interactWithToken(new GoldToken(2.0));
        Assertions.assertEquals(2.0 * 1.2, player.gold());
    }

    @Test
    void default_gainFactor_is_1_2() {
        var sluicebox = new SluiceboxToken();
        Assertions.assertEquals(1.2, sluicebox.gainFactor(), 0.001);
    }

    @Test
    void default_durability_is_5() {
        var sluicebox = new SluiceboxToken();
        Assertions.assertEquals(5, sluicebox.durability());
    }

    @Test
    void can_create_sluicebox_with_custom_gainFactor_and_durability() {
        var sluicebox = new SluiceboxToken(1.5, 3);
        Assertions.assertEquals(1.5, sluicebox.gainFactor(), 0.001);
        Assertions.assertEquals(3, sluicebox.durability());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new SluiceboxToken(0, 3));
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new SluiceboxToken(1.5, 0));
    }

    @Test
    void gainFactor_decreases_by_0_04_on_each_use() {
        var sluicebox = new SluiceboxToken();
        double initialGain = sluicebox.gainFactor();

        // First use
        sluicebox.use();
        Assertions.assertEquals(initialGain - 0.04, sluicebox.gainFactor(), 0.001);

        // Second use
        sluicebox.use();
        Assertions.assertEquals(initialGain - 0.08, sluicebox.gainFactor(), 0.001);
    }

    @Test
    void gainFactor_does_not_go_below_zero() {
        var sluicebox = new SluiceboxToken(0.1, 3);

        sluicebox.use();
        Assertions.assertEquals(0.06, sluicebox.gainFactor(), 0.001);

        sluicebox.use();
        Assertions.assertEquals(0.02, sluicebox.gainFactor(), 0.001);

        sluicebox.use();
        Assertions.assertEquals(0.0, sluicebox.gainFactor(), 0.001);

        sluicebox.use();
        Assertions.assertEquals(0.0, sluicebox.gainFactor(), 0.001);
    }

    @Test
    void sluicebox_use_decreases_durability_and_gainFactor() {
        var n = 3;
        var sluicebox = new SluiceboxToken(1.5, n);
        double expectedGain = 1.5;

        for (var i = 0; i < n; i++) {
            Assertions.assertTrue(sluicebox.durability() > 0);
            Assertions.assertEquals(expectedGain, sluicebox.gainFactor(), 0.001);
            sluicebox.use();
            expectedGain -= 0.04;
        }

        Assertions.assertEquals(0, sluicebox.durability());
        Assertions.assertTrue(sluicebox.isBroken());
    }

    @Test
    void broken_sluicebox_is_unusable() {
        var player = new Player();
        var goldToken = new GoldToken(2.0);
        var sluiceboxToken = new SluiceboxToken(1.5, 1);
        player.interactWithToken(sluiceboxToken);

        sluiceboxToken.use();

        player.interactWithToken(goldToken);

        Assertions.assertTrue(sluiceboxToken.isBroken());
        Assertions.assertEquals(2.0, player.gold());
    }

    @Test
    void sluicebox_affects_collected_gold_with_decreasing_gainFactor() {
        var player = new Player();
        var sluicebox = new SluiceboxToken(1.2, 3);
        player.interactWithToken(sluicebox);

        player.interactWithToken(new GoldToken(10.0));
        Assertions.assertEquals(12.0, player.gold(), 0.001);

        player.interactWithToken(new GoldToken(10.0));
        Assertions.assertEquals(23.6, player.gold(), 0.001);
    }

    @Test
    void sluicebox_cannot_be_repaired_by_anvil() {
        var player = new Player();
        var sluiceboxToken = new SluiceboxToken(1.2, 2);
        player.interactWithToken(sluiceboxToken);

        sluiceboxToken.use();
        int durabilityAfterUse = sluiceboxToken.durability();
        double gainFactorAfterUse = sluiceboxToken.gainFactor();

        player.interactWithToken(new AnvilToken());

        Assertions.assertEquals(durabilityAfterUse, sluiceboxToken.durability());
        Assertions.assertEquals(gainFactorAfterUse, sluiceboxToken.gainFactor(), 0.001);
    }

    @Test
    void can_use_sluicebox_with_gold() {
        new SluiceboxToken().useWith(new GoldToken())
                .ifWorking(() -> {
                    Assertions.assertTrue(true);
                })
                .ifBroken(Assertions::fail)
                .ifIdle(Assertions::fail);
    }

    @Test
    void can_use_sluicebox_with_other_than_gold_but_no_effect() {
        new SluiceboxToken().useWith(new EmptyToken())
                .ifWorking(Assertions::fail)
                .ifBroken(Assertions::fail)
                .ifIdle(() -> {
                    Assertions.assertTrue(true);
                });
    }

    @Test
    void broken_sluicebox_doesnt_work() {
        var sluiceboxToken = new SluiceboxToken(1.2, 1);
        sluiceboxToken.useWith(new GoldToken()).ifWorking(()->{});

        sluiceboxToken.useWith(new GoldToken())
                .ifWorking(Assertions::fail)
                .ifBroken(() -> {
                    Assertions.assertTrue(true);
                });
    }

    @Test
    void sluicebox_and_pickaxe_can_both_be_used() {
        var player = new Player();

        player.interactWithToken(new SluiceboxToken());

        player.interactWithToken(new GoldToken(10.0));
        double goldAfterSluicebox = player.gold();

        player.interactWithToken(new PickaxeToken());

        player.interactWithToken(new GoldToken(10.0));
        double goldAfterPickaxe = player.gold();

        Assertions.assertTrue(goldAfterPickaxe > goldAfterSluicebox + 10.0);
    }
}
