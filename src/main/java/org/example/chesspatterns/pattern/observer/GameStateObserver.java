package org.example.chesspatterns.pattern.observer;

import org.example.chesspatterns.pattern.state.GameState;

public interface GameStateObserver {
    void onStateChanged(GameState state);
}
