package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

public class GameOverState implements GameState {
    private final String message;

    public GameOverState(String message) {
        this.message = message;
    }


    @Override
    public boolean canSelectPiece(Piece piece) {
        return false;
    }

    @Override
    public void nextTurn(GameManager gameManager) {
    }

    @Override
    public String getStatusText() {
        return message;
    }
}
