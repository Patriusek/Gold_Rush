package edu.io.token;

import edu.io.player.Tool;

public class SluiceboxToken extends Token implements Tool {
    private double gainFactor;
    private int durability;
    private int initialDurability;
    private double initialGainFactor;
    private Token withToken;
    private boolean usedInCurrentChain = false;

    public SluiceboxToken() {
        super(Label.SLUICEBOX_TOKEN_LABEL);
        this.gainFactor = 1.2;
        this.initialGainFactor = 1.2;
        this.durability = 5;
        this.initialDurability = 5;
        this.withToken = new EmptyToken();
    }

    public SluiceboxToken(double gainFactor, int durability) {
        super(Label.SLUICEBOX_TOKEN_LABEL);
        if (gainFactor <= 0) {
            throw new IllegalArgumentException("Gain factor must be positive");
        }
        if (durability <= 0) {
            throw new IllegalArgumentException("Durability must be positive");
        }
        this.gainFactor = gainFactor;
        this.initialGainFactor = gainFactor;
        this.durability = durability;
        this.initialDurability = durability;
        this.withToken = new EmptyToken();
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
            gainFactor = Math.max(0.0, gainFactor - 0.04);
        }
    }

    @Override
    public boolean isBroken() {
        return durability <= 0;
    }

    @Override
    public SluiceboxToken useWith(Token withToken) {
        this.withToken = withToken;
        this.usedInCurrentChain = false;
        return this;
    }

    @Override
    public SluiceboxToken ifWorking(Runnable action) {
        if (withToken instanceof GoldToken && !isBroken() && !usedInCurrentChain) {
            action.run();
            if (durability > 0) {
                durability--;
                gainFactor = Math.max(0.0, gainFactor - 0.04);
            }
            usedInCurrentChain = true;
        }
        return this;
    }

    @Override
    public SluiceboxToken ifBroken(Runnable action) {
        if (withToken instanceof GoldToken && isBroken()) {
            action.run();
        }
        return this;
    }

    @Override
    public SluiceboxToken ifIdle(Runnable action) {
        if (!(withToken instanceof GoldToken) && !usedInCurrentChain) {
            action.run();
        }
        return this;
    }
}
