package org.example.chesspatterns.pattern.factory;

import org.example.chesspatterns.model.pieces.*;
import org.example.chesspatterns.pattern.strategy.*;

public class StandardPieceFactory implements PieceFactory {
    @Override
    public Piece createPiece(PieceType type, boolean isWhite) {
        return switch (type) {
            case PAWN -> new Pawn(isWhite, new PawnMoveStrategy());
            case ROOK -> new Rook(isWhite, new StraightMoveStrategy());
            case KNIGHT -> new Knight(isWhite, new LShapeMoveStrategy());
            case BISHOP -> new Bishop(isWhite, new DiagonalMoveStrategy());
            case QUEEN ->
                    new Queen(isWhite, new CompositeMoveStrategy(new StraightMoveStrategy(), new DiagonalMoveStrategy()));
            case KING -> new King(isWhite, new KingMoveStrategy());
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

}
