package edu.io.player;

import edu.io.token.*;

public class Player {
    private PlayerToken token;
    public final Gold gold = new Gold();
    private Tool tool = NoTool.getInstance();
    private final Shed shed = new Shed();

    public Player() {
    }

    public void assignToken(PlayerToken token) {
        this.token = token;
    }

    public PlayerToken token() {
        return token;
    }

    public double gold() {
        return gold.amount();
    }

    public void gainGold(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        gold.gain(amount);
        System.out.println("Collected " + amount + " oz of gold! Total: " + gold.amount() + " oz");
    }

    public void loseGold(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        gold.lose(amount);
    }

    public void interactWithToken(Token token) {
        switch (token) {
            case GoldToken goldToken -> collectGold(goldToken);
            case PickaxeToken pickaxeToken -> acquireTool(pickaxeToken);
            case SluiceboxToken sluiceboxToken -> acquireTool(sluiceboxToken);
            case AnvilToken anvilToken -> repairTool();
            default -> {
            }
        }
    }

    private void acquireTool(Tool tool) {
        shed.add(tool);
        this.tool = tool;
    }

    private void collectGold(GoldToken goldToken) {
        double amount = goldToken.amount();
        Tool currentTool = shed.getTool();

        if (currentTool instanceof PickaxeToken pickaxe) {
            usePickaxeOnGold(pickaxe, goldToken, amount);
        } else if (currentTool instanceof SluiceboxToken sluicebox) {
            useSluiceboxOnGold(sluicebox, goldToken, amount);
        } else {
            gainGold(amount);
        }
    }

    private void usePickaxeOnGold(PickaxeToken pickaxe, GoldToken goldToken, double baseAmount) {
        pickaxe.useWith(goldToken)
                .ifWorking(() -> gainGold(baseAmount * pickaxe.gainFactor()))
                .ifBroken(() -> gainGold(baseAmount))
                .ifIdle(() -> gainGold(baseAmount));
    }

    private void useSluiceboxOnGold(SluiceboxToken sluicebox, GoldToken goldToken, double baseAmount) {
        sluicebox.useWith(goldToken)
                .ifWorking(() -> gainGold(baseAmount * sluicebox.gainFactor()))
                .ifBroken(() -> gainGold(baseAmount))
                .ifIdle(() -> gainGold(baseAmount));
    }

    private void repairTool() {
        Tool currentTool = shed.getTool();
        if (currentTool instanceof Repairable repairableTool) {
            repairableTool.repair();
        }
    }

    public Shed shed() {
        return shed;
    }

    public Tool tool() {
        return tool;
    }
}
