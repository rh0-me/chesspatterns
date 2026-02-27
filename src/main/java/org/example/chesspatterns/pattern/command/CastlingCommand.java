package org.example.chesspatterns.pattern.command;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class CastlingCommand implements Command {
    private final Board board;
    private final int row;
    private final int kingStartCol, kingEndCol;
    private final int rookStartCol, rookEndCol;

    private Piece king;
    private Piece rook;


    public CastlingCommand(Board board, int row, int kingStartCol, int kingEndCol) {
        this.board = board;
        this.row = row;
        this.kingStartCol = kingStartCol;
        this.kingEndCol = kingEndCol;

        // Turm-Positionen berechnen (Kurze oder lange Rochade?)
        if (kingEndCol > kingStartCol) { // Kurze Rochade (Königsg-Flügel)
            this.rookStartCol = 7;
            this.rookEndCol = kingEndCol - 1; // Turm landet links neben dem König
        } else { // Lange Rochade (Damen-Flügel)
            this.rookStartCol = 0;
            this.rookEndCol = kingEndCol + 1; // Turm landet rechts neben dem König
        }
    }

    // Für Undo merken, ob sie sich VOR diesem Zug schon mal bewegt hatten (sollte false sein)
    private boolean kingHadMoved;
    private boolean rookHadMoved;

    @Override
    public void execute() {
        king = board.getPiece(row, kingStartCol);
        rook = board.getPiece(row, rookStartCol);

        kingHadMoved = king.hasMoved();
        rookHadMoved = rook.hasMoved();

        // 1. König bewegen
        board.setPiece(row, kingEndCol, king);
        board.setPiece(row, kingStartCol, null);
        king.setHasMoved(true);

        // 2. Turm bewegen
        board.setPiece(row, rookEndCol, rook);
        board.setPiece(row, rookStartCol, null);
        rook.setHasMoved(true);

        board.notifyObservers();
    }

    @Override
    public void undo() {
        // Alles exakt rückwärts
        board.setPiece(row, kingStartCol, king);
        board.setPiece(row, kingEndCol, null);
        king.setHasMoved(kingHadMoved);

        board.setPiece(row, rookStartCol, rook);
        board.setPiece(row, rookEndCol, null);
        rook.setHasMoved(rookHadMoved);

        board.notifyObservers();
    }
}
