package edu.io.player;

import edu.io.token.Token;

public interface Tool {
    Tool useWith(Token withToken);
    Tool ifWorking(Runnable action);
    Tool ifBroken(Runnable action);
    Tool ifIdle(Runnable action);
    boolean isBroken();
}
