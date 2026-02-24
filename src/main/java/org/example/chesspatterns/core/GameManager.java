package org.example.chesspatterns.core;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.command.Move;

public class GameManager {

    private static GameManager instance;

    public int gameState = 0; // 0 = ongoing, 1 = white wins, 2 = black wins, 3 = stalemate

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Board board;
    private boolean isWhiteToMove = true;

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

        checkGameStatus();
    }

    private void checkGameStatus() {
        boolean inCheck = board.checkScanner.isKingInCheck(isWhiteToMove);
        boolean anyMovePossible = hasAnyValidMoves(isWhiteToMove);

        if (!anyMovePossible) {
            if (inCheck) {
                // Keine Züge + Schach = Matt
                gameState = isWhiteToMove ? 2 : 1; // Wenn Weiß am Zug ist und Matt ist, gewinnt Schwarz (2)
                System.out.println("SCHACHMATT! " + (isWhiteToMove ? "Schwarz" : "Weiß") + " gewinnt.");
                javax.swing.JOptionPane.showMessageDialog(null, "SCHACHMATT! Spiel vorbei.");
            } else {
                // Keine Züge + Kein Schach = Patt
                gameState = 3;
                System.out.println("PATT! Unentschieden.");
                javax.swing.JOptionPane.showMessageDialog(null, "Patt! Unentschieden.");
            }
        }
    }

    private boolean hasAnyValidMoves(boolean isWhiteToMove) {
        for (Piece p : board.getPieces()) {
            if (p.isWhite == isWhiteToMove) {
                // Probiere JEDES Feld auf dem Brett
                for (int r = 0; r < 8; r++) {
                    for (int c = 0; c < 8; c++) {
                        // Erstelle Test-Move
                        Move move = new Move(board, p, c, r);
                        if (board.isValidMove(move)) {
                            return true; // Es gibt mindestens einen Rettungsweg
                        }
                    }
                }
            }
        }
        return false;
    }
}
