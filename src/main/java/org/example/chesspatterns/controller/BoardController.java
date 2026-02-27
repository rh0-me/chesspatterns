package org.example.chesspatterns.controller;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.state.WhiteTurnState;
import org.example.chesspatterns.view.BoardView;

import java.util.ArrayList;
import java.util.List;

public class BoardController {
    private int selectedRow = -1;
    private int selectedCol = -1;

    private final BoardView view;
    private final GameManager gameManager;

    public BoardController(BoardView view, GameManager gameManager) {
        this.view = view;
        this.gameManager = gameManager;
    }

    public void handleSquareClick(int row, int col) {
        // 1. Klick: Eine Figur auswählen
        if (selectedRow == -1 && selectedCol == -1) {
            Piece clickedPiece = gameManager.getBoard().getPiece(row, col);

            boolean isWhiteTurn = gameManager.getCurrentState() instanceof WhiteTurnState;
            // Nur markieren, wenn da auch eine Figur steht
            if (clickedPiece != null) {
                selectedRow = row;
                selectedCol = col;
                view.highlightSquare(row, col); // Nutzt unseren Decorator in der View!

                List<int[]> validMoves = calculateValidMoves(clickedPiece, row, col);
                view.highlightValidMoves(validMoves);
            }
        }
        // 2. Klick: Zielfeld gewählt
        else {
            // GameManager versucht den Zug auszuführen (State Pattern greift hier!)
            boolean success = gameManager.attemptMove(selectedRow, selectedCol, row, col);

            // Auswahl zurücksetzen
            selectedRow = -1;
            selectedCol = -1;
            view.clearHighlights();
        }
    }

    private List<int[]> calculateValidMoves(Piece piece, int startRow, int startCol) {
        List<int[]> validMoves = new ArrayList<>();
        Board board = gameManager.getBoard();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // Das Strategy Pattern in Aktion! Wir fragen die Figur einfach, ob der Zug erlaubt ist.
                if (piece.canMove(board, startRow, startCol, r, c)) {
                    if (!board.wouldMoveCauseCheck(startRow, startCol, r, c, piece.isWhite()))
                        validMoves.add(new int[]{r, c});
                }
            }
        }
        return validMoves;
    }
}
