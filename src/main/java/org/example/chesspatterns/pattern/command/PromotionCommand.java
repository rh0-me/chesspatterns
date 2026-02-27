package org.example.chesspatterns.pattern.command;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class PromotionCommand implements Command {
    private final Board board;
    private final int startRow, startCol, endRow, endCol;

    private final Piece promotedPiece;
    private Piece originalPawn;
    private Piece capturedPiece;

    public PromotionCommand(Board board, int startRow, int startCol, int endRow, int endCol, Piece promotedPiece) {
        this.board = board;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.promotedPiece = promotedPiece;
    }

    @Override
    public void execute() {
        originalPawn = board.getPiece(startRow, startCol);
        capturedPiece = board.getPiece(endRow, endCol);

        board.setPiece(startRow, startCol, null);

        board.setPiece(endRow, endCol, promotedPiece);

        board.notifyObservers();
    }

    @Override
    public void undo() {
        board.setPiece(endRow, endCol, capturedPiece);

        board.setPiece(startRow, startCol, originalPawn);

        board.notifyObservers();
    }
}
