package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.pieces.Piece;

import javax.swing.*;

public class GameOverState implements GameState {
    private final String resultMessage;

    public GameOverState(String resultMessage) {
        this.resultMessage = resultMessage;

        JOptionPane.showMessageDialog(null, resultMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public boolean handleMove(GameManager context, int startRow, int startCol, int endRow, int endCol) {
        System.out.println("The game is over: " + resultMessage);
        return false;
    }

    @Override
    public String getStateName() {
        return "Game Over";
    }
}
