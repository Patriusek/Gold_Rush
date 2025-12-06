package edu.io.token;

import edu.io.player.Repairable;
import edu.io.player.Tool;

public class PickaxeToken extends Token implements Tool, Repairable {
    private double gainFactor;
    private int durability;
    private int initialDurability;
    private Token withToken;
    private boolean usedInCurrentChain = false;

    public PickaxeToken() {
        super(Label.PICKAXE_TOKEN_LABEL);
        this.gainFactor = 1.5;
        this.durability = 3;
        this.initialDurability = 3;
        this.withToken = new EmptyToken();
    }

    public PickaxeToken(double gainFactor) {
        this();
        if (gainFactor <= 0) {
            throw new IllegalArgumentException("Gain factor must be positive");
        }
        this.gainFactor = gainFactor;
    }

    public PickaxeToken(double gainFactor, int durability) {
        this(gainFactor);
        if (durability <= 0) {
            throw new IllegalArgumentException("Durability must be positive");
        }
        this.durability = durability;
        this.initialDurability = durability;
    }

    public double gainFactor() {
        return gainFactor;
    }

    public int durability() {
        return durability;
    }

    public void use() {
        if (durability > 0) {
            durability--;
        }
    }

    @Override
    public boolean isBroken() {
        return durability <= 0;
    }

    @Override
    public PickaxeToken useWith(Token withToken) {
        this.withToken = withToken;
        this.usedInCurrentChain = false;
        return this;
    }

    @Override
    public PickaxeToken ifWorking(Runnable action) {
        if (withToken instanceof GoldToken && !isBroken() && !usedInCurrentChain) {
            action.run();
            if (durability > 0) {
                durability--;
            }
            usedInCurrentChain = true;
        }
        return this;
    }

    @Override
    public PickaxeToken ifBroken(Runnable action) {
        if (withToken instanceof GoldToken && isBroken()) {
            action.run();
        }
        return this;
    }

    @Override
    public PickaxeToken ifIdle(Runnable action) {
        if (!(withToken instanceof GoldToken) && !usedInCurrentChain) {
            action.run();
        }
        return this;
    }

    @Override
    public void repair() {
        this.durability = initialDurability;
    }
}