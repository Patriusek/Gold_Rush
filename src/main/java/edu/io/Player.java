package edu.io;

import edu.io.token.PlayerToken;
import edu.io.token.Token;

public class Player {
    private PlayerToken token;
    private double gold;

    public Player() {
        this.gold = 0.0;
    }

    public void assignToken(PlayerToken token) {
        this.token = token;
    }

    public PlayerToken token() {
        return token;
    }

    public double gold() {
        return gold;
    }

    public void gainGold(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException();

        if (amount > 0) {
            this.gold += amount;
            System.out.println("Collected " + amount + " oz of gold! Total: " + this.gold + " oz");
        }
    }

    public void loseGold(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException();

        if (amount > 0 && amount <= this.gold) {
            this.gold -= amount;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void interactWithToken(Token token) {
        if (token instanceof edu.io.token.GoldToken goldToken) {
            gainGold(goldToken.amount());
        }
    }
}
