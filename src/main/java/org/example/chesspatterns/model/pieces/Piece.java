package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.strategy.MoveStrategy;

//public abstract class Piece {
//
//    public int row, column;
//    public boolean isWhite;
//    public boolean isFirstMove = true;
//
//    // We keep the board reference for move logic (collision etc), 
//    // BUT we do not access tileSize anymore.
//    protected Board board;
//
//    public Piece(Board board) {
//        this.board = board;
//    }
//
//    public Piece(Board board, int col, int row, boolean isWhite) {
//        this.board = board;
//        this.row = row;
//        this.column = col;
//        this.isWhite = isWhite;
//    }
//
//    public void updatePosition(int col, int row) {
//        this.column = col;
//        this.row = row;
//    }
//
//    // Helper so the View knows what image to load (e.g., "P-W.png")
//    public String getImageName() {
//        String type = "";
//        switch (this) {
//            case Pawn _ -> type = "P";
//            case Rook _ -> type = "R";
//            case Knight _ -> type = "N";
//            case Bishop _ -> type = "B";
//            case Queen _ -> type = "Q";
//            case King _ -> type = "K";
//            default -> {
//            }
//        }
//
//        return type + "-" + (isWhite ? "W" : "B") + ".png";
//    }
//
//    // --- LOGIC ONLY BELOW ---
//
//    public final boolean isValidMove(int targetCol, int targetRow) {
//        if (targetCol < 0
//                || targetCol >= board.boardWidthInTiles
//                || targetRow < 0
//                || targetRow >= board.boardHeightInTiles) {
//            return false;
//        }
//        Piece targetPiece = board.getPieceAtLocation(targetCol, targetRow);
//        if (targetPiece != null && targetPiece.isWhite == this.isWhite) {
//            return false;
//        }
//
//        if (!isMovePatternValid(targetCol, targetRow)) {
//            return false;
//        }
//
//        if (moveCollidesWithPieces(targetCol, targetRow)) {
//            return false;
//        }
//
//        return true;
//    }
//
//    protected abstract boolean isMovePatternValid(int targetCol, int targetRow);
//
//    public boolean moveCollidesWithPieces(int targetCol, int targetRow) {
//        return false;
//    }
//    
//    public abstract Piece copyWithBoard(Board newBoard);
//}

public abstract class Piece {
    protected boolean isWhite;
    protected boolean hasMoved = false;
    protected MoveStrategy moveStrategy;


    public Piece(boolean isWhite, MoveStrategy moveStrategy) {
        this.isWhite = isWhite;
        this.moveStrategy = moveStrategy;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean canMove(Board board, int startRow, int startCol, int endRow, int endCol) {
        return moveStrategy.isValidMove(board, startRow, startCol, endRow, endCol, isWhite);
    }

    public abstract String getImageName();

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
