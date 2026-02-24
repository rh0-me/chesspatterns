package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

public class PieceFactory {

    public Piece createPiece(PieceType type, Board board, int col, int row, boolean isWhite) {
        return switch (type) {
            case PAWN -> new Pawn(board, col, row, isWhite);
            case ROOK -> new Rook(board, col, row, isWhite);
            case KNIGHT -> new Knight(board, col, row, isWhite);
            case BISHOP -> new Bishop(board, col, row, isWhite);
            case QUEEN -> new Queen(board, col, row, isWhite);
            case KING -> new King(board, col, row, isWhite);
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}

