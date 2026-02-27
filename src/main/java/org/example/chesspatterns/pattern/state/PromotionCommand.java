package org.example.chesspatterns.pattern.state;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.command.Command;

public class PromotionCommand implements Command {
    private final Board board;
    private final int startRow, startCol, endRow, endCol;

    private final Piece promotedPiece; // Die neue Figur (z.B. Dame)
    private Piece originalPawn;        // Der alte Bauer (fürs Undo)
    private Piece capturedPiece;       // Falls beim Umwandeln geschlagen wurde

    public PromotionCommand(Board board, int startRow, int startCol, int endRow, int endCol, Piece promotedPiece) {
        this.board = board;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.promotedPiece = promotedPiece;
    }

    @Override
    public void execute() {
        originalPawn = board.getPiece(startRow, startCol);
        capturedPiece = board.getPiece(endRow, endCol);

        // 1. Startfeld leeren (Bauer verschwindet)
        board.setPiece(startRow, startCol, null);

        // 2. Zielfeld mit der NEUEN Figur besetzen
        board.setPiece(endRow, endCol, promotedPiece);

        board.notifyObservers();
    }

    @Override
    public void undo() {
        // 1. Zielfeld zurücksetzen (geschlagene Figur oder null)
        board.setPiece(endRow, endCol, capturedPiece);

        // 2. Startfeld wieder mit dem alten Bauern besetzen
        board.setPiece(startRow, startCol, originalPawn);

        board.notifyObservers();
    }
}
