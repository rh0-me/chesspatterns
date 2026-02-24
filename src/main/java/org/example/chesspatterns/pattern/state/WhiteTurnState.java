package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

public class WhiteTurnState implements GameState {
    @Override
    public boolean canSelectPiece(Piece piece) {
        return piece.isWhite;
    }

    @Override
    public void nextTurn(GameManager gameManager) {
        gameManager.setState(new BlackTurnState());

    }

    @Override
    public String getStatusText() {
        return "White's Turn";
    }
}
