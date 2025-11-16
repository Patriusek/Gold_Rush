package edu.io.token;

public abstract class Token {
    protected String label;

    public Token(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
