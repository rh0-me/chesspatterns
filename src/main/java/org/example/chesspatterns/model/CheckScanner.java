//package org.example.chesspatterns.model;
//
//import org.example.chesspatterns.model.board.Board;
//import org.example.chesspatterns.model.pieces.Piece;
//
//public class CheckScanner {
//
//    private final Board board;
//
//    public CheckScanner(Board board) {
//        this.board = board;
//    }
//
//    public boolean isKingInCheck(boolean isWhiteKing) {
//        Piece king = board.findKing(isWhiteKing);
//
//        assert king != null : "King should not be null in a valid game state";
//
//        for (Piece p : board.getPieces()) {
//            if (p.isWhite != isWhiteKing) {
//                if (canPieceAttackKing(p, king)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean canPieceAttackKing(Piece attacker, Piece king) {
//        return attacker.isValidMove(king.column, king.row);
//    }
//}
