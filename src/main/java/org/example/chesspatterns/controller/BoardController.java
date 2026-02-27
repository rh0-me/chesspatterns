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
        // 1st click: select piece
        if (selectedRow == -1 && selectedCol == -1) {
            Piece clickedPiece = gameManager.getBoard().getPiece(row, col);

            boolean isWhiteTurn = gameManager.getCurrentState() instanceof WhiteTurnState;

            if (clickedPiece != null && clickedPiece.isWhite() == isWhiteTurn) {
                selectedRow = row;
                selectedCol = col;
                view.highlightSquare(row, col);

                List<int[]> validMoves = calculateValidMoves(clickedPiece, row, col);
                view.highlightValidMoves(validMoves);
            }
        }
        // 2nd click: attempt move
        else {
            gameManager.attemptMove(selectedRow, selectedCol, row, col);

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
                if (piece.canMove(board, startRow, startCol, r, c)) {
                    if (!board.wouldMoveCauseCheck(startRow, startCol, r, c, piece.isWhite()))
                        validMoves.add(new int[]{r, c});
                }
            }
        }
        return validMoves;
    }

    public void resetSelection() {
        selectedRow = -1;
        selectedCol = -1;
        view.clearHighlights();
    }
}
