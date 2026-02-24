package org.example.chesspatterns.pattern.command;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.*;

import java.awt.*;

public class Move implements Command {
    public final int oldRow, oldCol;
    public final int newRow, newCol;


    public Piece piece;
    public Board board;


    private Piece capturedPiece;
    private Piece promotedPiece;
    private final boolean wasFirstMove;
    private final Point oldEnPassantTile;
    private Move castlingRookMove;

    public Move(Board board, Piece piece, int toCol, int toRow) {
        this.board = board;
        this.piece = piece;

        this.oldRow = piece.row;
        this.oldCol = piece.column;
        this.newRow = toRow;
        this.newCol = toCol;

        this.wasFirstMove = piece.isFirstMove;
        this.oldEnPassantTile = board.enPassantTile;

        this.capturedPiece = board.getPieceAtLocation(toCol, toRow);
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    @Override
    public void execute() {

        boolean isEnPassantMove = isEnPassantCapture();
        if (isEnPassantMove) {
            int direction = piece.isWhite ? -1 : 1;
            this.capturedPiece = board.getPieceAtLocation(newCol, newRow - direction);
        } else {
            this.capturedPiece = board.getPieceAtLocation(newCol, newRow);
        }

        updateEnPassantState();

        if (capturedPiece != null) {
            board.capturePiece(capturedPiece);
        }

        // 3. Rochade (Castling) Check
        if (piece instanceof King && Math.abs(newCol - oldCol) == 2) {
            performCastling();
        }

        piece.updatePosition(newCol, newRow);
        piece.isFirstMove = false;

        handlePromotion();

    }

    private void performCastling() {
        // Logik: Wir erzeugen einen ZWEITEN Move-Befehl für den Turm und führen ihn aus.
        int rookCol = (newCol > oldCol) ? 7 : 0; // Kurze oder lange Rochade?
        int rookTargetCol = (newCol > oldCol) ? newCol - 1 : newCol + 1;

        Piece rook = board.getPieceAtLocation(rookCol, newRow);
        if (rook instanceof Rook) {
            // Rekursiver Move für den Turm (ohne En Passant Logik etc.)
            castlingRookMove = new Move(board, rook, rookTargetCol, newRow);
            castlingRookMove.execute();
        }
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

        if (castlingRookMove != null) {
            castlingRookMove.undo();
        }

        board.enPassantTile = oldEnPassantTile;

    }


    private boolean isEnPassantCapture() {
        if (piece instanceof Pawn && oldEnPassantTile != null) {
            // Wenn wir auf das En Passant Feld ziehen
            return newCol == oldEnPassantTile.x && newRow == oldEnPassantTile.y;
        }
        return false;
    }

    private void updateEnPassantState() {
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
        int endRow = piece.isWhite ? 0 : board.boardHeightInTiles - 1;

        if (endRow == newRow) {
            board.capturePiece(piece);

            PieceType selectedType = GameManager.getInstance().askPromotionChoice(piece.isWhite);

            this.promotedPiece = board.promotePiece(selectedType, piece.isWhite, newCol, newRow);
            board.addPiece(promotedPiece);
        }

    }
}
