package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

public abstract class Piece {

    public int row, column;
    public boolean isWhite;
    public boolean isFirstMove = true;

    // We keep the board reference for move logic (collision etc), 
    // BUT we do not access tileSize anymore.
    protected Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public Piece(Board board, int col, int row, boolean isWhite) {
        this.board = board;
        this.row = row;
        this.column = col;
        this.isWhite = isWhite;
    }

    public void updatePosition(int col, int row) {
        this.column = col;
        this.row = row;
    }

    // Helper so the View knows what image to load (e.g., "P-W.png")
    public String getImageName() {
        String type = "";
        switch (this) {
            case Pawn _ -> type = "P";
            case Rook _ -> type = "R";
            case Knight _ -> type = "N";
            case Bishop _ -> type = "B";
            case Queen _ -> type = "Q";
            case King _ -> type = "K";
            default -> {
            }
        }

        return type + "-" + (isWhite ? "W" : "B") + ".png";
    }

    // --- LOGIC ONLY BELOW ---

    public boolean isValidMove(int targetCol, int targetRow) {
        if (targetCol < 0 || targetCol >= board.boardWidthInTiles || targetRow < 0 || targetRow >= board.boardHeightInTiles) {
            return false;
        }
        Piece targetPiece = board.getPieceAtLocation(targetCol, targetRow);
        if (targetPiece != null && targetPiece.isWhite == this.isWhite) {
            return false;
        }
        return true;
    }

    public boolean moveCollidesWithPieces(int targetCol, int targetRow) {
        return false;
    }
}
