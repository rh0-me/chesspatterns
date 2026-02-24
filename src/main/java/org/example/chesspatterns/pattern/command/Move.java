package org.example.chesspatterns.pattern.command;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Pawn;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.model.pieces.Queen;

public class Move implements Command {
    public final int oldRow, oldCol;
    public final int newRow, newCol;


    public Piece piece;
    public Board board;


    private Piece capturedPiece;
    private Piece promotedPiece;
    private boolean wasFirstMove;
    private boolean isEnPassant;

    public Move(Board board, Piece piece, int toCol, int toRow) {
        this.board = board;
        this.piece = piece;

        this.oldRow = piece.row;
        this.oldCol = piece.column;

        this.newRow = toRow;
        this.newCol = toCol;

        this.wasFirstMove = piece.isFirstMove;

        this.capturedPiece = board.getPieceAtLocation(toCol, toRow);
    }

   public Piece getCapturedPiece() {
        return capturedPiece;
    }
    
    @Override
    public void execute() {

        handleEnPassant();

        piece.updatePosition(newCol, newRow);
        piece.isFirstMove = false;

        if (capturedPiece != null) {
            board.capturePiece(capturedPiece);
        }

        updateEnPassantTile();

        handlePromotion();
//        boolean isDoubleStepPawnMove = piece instanceof Pawn && piece.isFirstMove && Math.abs(newRow - piece.row) == 2;
//
//
//        if (piece instanceof Pawn) {
//            int direction = piece.isWhite ? -1 : 1;
//
//            boolean isEnPassantMove =
//                    board.enPassantTile != null
//                            && capturedPiece == null
//                            && newCol == board.enPassantTile.x
//                            && newRow == board.enPassantTile.y
//                            && Math.abs(oldCol - newCol) == 1
//                            && piece.row == board.enPassantTile.y - direction;
//
//            if (isEnPassantMove) {
//                Piece capturedPawn = board.getPieceAtLocation(board.enPassantTile.x, board.enPassantTile.y - direction);
//                if (capturedPawn instanceof Pawn) {
//                    board.capturePiece(capturedPawn);
//                }
//            }
//        }
//
//        if (!isDoubleStepPawnMove) {
//            board.enPassantTile = null;
//        }
//
//        piece.updatePosition(newCol, newRow);
//        piece.isFirstMove = false;
//
//        if (capturedPiece != null) {
//            board.capturePiece(capturedPiece);
//        }
    }

    @Override
    public void undo() {
// A. Promotion rückgängig machen (Dame weg, Bauer wieder hin)
        if (promotedPiece != null) {
            board.removePiece(promotedPiece);
            board.addPiece(piece); // Der alte Bauer kommt zurück
        }

        // B. Figur zurückbewegen
        piece.updatePosition(oldCol, oldRow);
        piece.isFirstMove = wasFirstMove;

        // C. Geschlagene Figur wiederherstellen
        if (capturedPiece != null) {
            board.addPiece(capturedPiece);
        }

        // D. En Passant Tile Logik ist komplex beim Undo, 
        // oft reicht es, es auf null zu setzen oder man müsste den alten Zustand im Board speichern.
        // Fürs erste ignorieren wir das Zurücksetzen des globalen enPassantTiles.
    }

    private void handleEnPassant() {
        if (piece instanceof Pawn) {
            // Prüfung: Ist das ein En Passant Zug?
            if (board.enPassantTile != null && newCol == board.enPassantTile.x && newRow == board.enPassantTile.y && capturedPiece == null // Zielfeld ist leer...
                    && Math.abs(oldCol - newCol) == 1) { // ...aber wir bewegen uns diagonal

                this.isEnPassant = true;

                // Beim En Passant ist die geschlagene Figur NICHT auf dem Zielfeld, sondern "dahinter"
                int direction = piece.isWhite ? -1 : 1;
                this.capturedPiece = board.getPieceAtLocation(newCol, newRow - direction);
            }
        }
    }

    private void updateEnPassantTile() {
// Prüfen ob Bauer 2 Felder vorrückt -> En Passant ermöglichen
        if (piece instanceof Pawn && Math.abs(newRow - oldRow) == 2) {
            int direction = piece.isWhite ? -1 : 1;
            // Das Feld HINTER dem Bauern ist das En Passant Ziel
            board.enPassantTile = new java.awt.Point(oldCol, oldRow + direction);
        } else {
            board.enPassantTile = null; // En Passant verfällt sonst
        }
    }

    private void handlePromotion() {

        if (!(piece instanceof Pawn)) return;

        if ((piece.isWhite && newRow == 0) || (!piece.isWhite && newRow == board.boardHeightInTiles - 1)) {
            board.capturePiece(piece);

            this.promotedPiece = new Queen(board, newCol, newRow, piece.isWhite);
            board.addPiece(promotedPiece);
        }

    }
}
