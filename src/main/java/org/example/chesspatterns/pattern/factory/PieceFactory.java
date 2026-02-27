package org.example.chesspatterns.pattern.factory;

import org.example.chesspatterns.model.pieces.Piece;

public interface PieceFactory {
    Piece createPiece(PieceType type, boolean isWhite);
}

