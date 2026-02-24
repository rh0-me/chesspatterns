package org.example.chesspatterns.core;

import org.example.chesspatterns.model.pieces.PieceType;

public interface PromotionHandler {
    PieceType handlePromotion(boolean isWhite);
}
