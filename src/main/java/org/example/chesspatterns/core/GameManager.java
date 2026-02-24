package org.example.chesspatterns.core;

import org.example.chesspatterns.model.board.Board;

public class GameManager {

    private static GameManager instance;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Board board; // Das Spielfeld
    private boolean isWhiteToMove = true; // Wer ist dran?

    public void initializeGame(Board board) {
        this.board = board;
        this.isWhiteToMove = true;
    }

    public boolean isWhiteTurn() {
        return isWhiteToMove;
    }

    public void nextTurn() {
        isWhiteToMove = !isWhiteToMove;
        System.out.println("Spielerwechsel: Jetzt ist " + (isWhiteToMove ? "Weiß" : "Schwarz") + " am Zug.");
    }
}
