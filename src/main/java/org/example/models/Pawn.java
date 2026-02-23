package org.example.models;

import org.example.Board;

public class Pawn extends Piece {
    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

        this.sprite = getSprite(isWhite ? "P-W.png" : "P-B.png");
    }

    @Override
    public boolean isValidMove(int targetCol, int targetRow) {
        if (!super.isValidMove(targetCol, targetRow)) {
            return false;
        }

        int colDiff = targetCol - this.column;
        int rowDiff = targetRow - this.row;

        // Pawns move forward (direction depends on color)
        int direction = isWhite ? -1 : 1;

        // Standard move: one square forward
        if (colDiff == 0 && rowDiff == direction) {
            return board.getPieceAtLocation(targetCol, targetRow) == null; // Must be empty
        }

        // First move: two squares forward
        if (colDiff == 0 && rowDiff == 2 * direction && isFirstMove) {
            // Both squares must be empty
            return board.getPieceAtLocation(targetCol, targetRow) == null &&
                    board.getPieceAtLocation(targetCol, this.row + direction) == null;
        }

        // Capture move: one square diagonally forward
        if (Math.abs(colDiff) == 1 && rowDiff == direction) {
            Piece targetPiece = board.getPieceAtLocation(targetCol, targetRow);
            return targetPiece != null && targetPiece.isWhite != this.isWhite; // Must be an opponent's piece
        }

        if (board.getTileNum(targetCol, targetRow) == board.enPassantTileNum
                && targetCol == this.column + colDiff
                && rowDiff == direction
                && board.getPieceAtLocation(targetCol, targetRow - direction) instanceof Pawn) {
            return true;
        }

        return false; // Invalid move
    }
}
