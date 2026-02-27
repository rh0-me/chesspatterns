package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

import javax.swing.*;

public class GameOverState implements GameState {
    private final String resultMessage;

    public GameOverState(String resultMessage) {
        this.resultMessage = resultMessage;

        // Zeigt ein Pop-Up Fenster an, wenn das Spiel vorbei ist
        JOptionPane.showMessageDialog(null, resultMessage, "Spielende", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public boolean handleMove(GameManager context, int startRow, int startCol, int endRow, int endCol) {
        System.out.println("Das Spiel ist bereits vorbei: " + resultMessage);
        return false; // Blockiert jeden Klick
    }

    @Override
    public String getStateName() {
        return "Spiel vorbei";
    }
}
