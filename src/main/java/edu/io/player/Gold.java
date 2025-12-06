package edu.io.player;

public class Gold {
    private double amount;

    public Gold() {
        this(0.0);
    }

    public Gold(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Gold amount cannot be negative");
        }
        this.amount = amount;
    }

    public double amount() {
        return amount;
    }

    public void gain(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Gain amount cannot be negative");
        }
        this.amount += amount;
    }

    public void lose(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Lose amount cannot be negative");
        }
        if (amount > this.amount) {
            throw new IllegalArgumentException("Cannot lose more gold than available");
        }
        this.amount -= amount;
    }
}
