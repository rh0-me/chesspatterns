package org.example.chesspatterns.core;

import org.example.chesspatterns.pattern.factory.PieceType;

public interface PromotionHandler {
    PieceType handlePromotion(boolean isWhite);
}
