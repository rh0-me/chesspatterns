package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

public class King extends Piece {
    public King(Board board, int col, int row, boolean isWhite) {
        super(board, col, row, isWhite);

    }

    @Override
    public boolean isMovePatternValid(int targetCol, int targetRow) {
        int colDiff = Math.abs(targetCol - this.column);
        int rowDiff = Math.abs(targetRow - this.row);

        if ((colDiff <= 1 && rowDiff <= 1))
            return true;

        if (isFirstMove
                && row == targetRow
                && colDiff == 2) {

            // Castling logic: Check if the path is clear and the rook is in place
            int rookCol = (targetCol > column) ? 7 : 0; // Kingside or queenside
            Piece rook = board.getPieceAtLocation(rookCol, row);


            if (rook instanceof Rook && rook.isWhite == this.isWhite && rook.isFirstMove) {
                // Check if the squares between king and rook are empty
                int step = (targetCol > column) ? 1 : -1; // Direction to check
                for (int c = this.column + step; c != rookCol; c += step) {
                    if (board.getPieceAtLocation(c, row) != null) {
                        return false; // Path is not clear
                    }
                }
                return true; // Castling is valid
            }
        }

        return false;
    }

    @Override
    public Piece copyWithBoard(Board newBoard) {
        return new King(newBoard, this.column, this.row, this.isWhite);
    }
}
