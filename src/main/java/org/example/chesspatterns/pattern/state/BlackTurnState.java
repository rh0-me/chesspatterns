package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

public class BlackTurnState implements GameState {
    @Override
    public boolean canSelectPiece(Piece piece) {
        return !piece.isWhite; // Nur schwarze Figuren wählbar
    }

    @Override
    public void nextTurn(GameManager gameManager) {

        // Wechsel zu Weiß
        gameManager.setState(new WhiteTurnState());

        // Nach dem Wechsel prüfen: Hat Weiß verloren?
        gameManager.checkGameStatusForCurrentPlayer();
    }

    @Override
    public String getStatusText() {
        return "Black's Turn";
    }
}
