package org.example.chesspatterns.pattern.command;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.*;

import java.awt.*;

public class MoveCommand implements Command {
    private final Board board;
    private final int startRow, startCol, endRow, endCol;
    private boolean movedPieceHasMoved;

    private Piece movedPiece;
    private Piece capturedPiece;

    public MoveCommand(Board board, int startRow, int startCol, int endRow, int endCol) {
        this.board = board;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    @Override
    public void execute() {
        movedPiece = board.getPiece(startRow, startCol);
        capturedPiece = board.getPiece(endRow, endCol);
        
        movedPieceHasMoved = movedPiece.hasMoved();

        board.setPiece(endRow, endCol, movedPiece);
        board.setPiece(startRow, startCol, null); // Startfeld leeren

        board.notifyObservers();
    }

    @Override
    public void undo() {
        board.setPiece(startRow, startCol, movedPiece);
       
        movedPiece.setHasMoved(movedPieceHasMoved);

        board.setPiece(endRow, endCol, capturedPiece);

        board.notifyObservers();
    }
}
