package org.example;

import org.example.models.Piece;

public class Move {
    final int oldRow, oldCol;
    final int newRow, newCol;


    Piece piece;
    Piece targetPiece;

    public Move(Board board, Piece piece, int toCol, int toRow) {
        this.oldRow = piece.row;
        this.oldCol = piece.column;

        this.newRow = toRow;
        this.newCol = toCol;

        this.piece = piece;

        this.targetPiece = board.getPieceAtLocation(toCol, toRow);
    }

}
