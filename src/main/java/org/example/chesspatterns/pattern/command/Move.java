package org.example.chesspatterns.pattern.command;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

public class Move implements Command {
    public final int oldRow, oldCol;
    public final int newRow, newCol;


    public Piece piece;
    public Piece targetPiece;
    Board board;
    boolean isFirstMove;

    public Move(Board board, Piece piece, int toCol, int toRow) {
        this.board = board;
        this.piece = piece;

        this.oldRow = piece.row;
        this.oldCol = piece.column;

        this.newRow = toRow;
        this.newCol = toCol;


        this.targetPiece = board.getPieceAtLocation(toCol, toRow);
        this.isFirstMove = piece.isFirstMove;
    }

    @Override
    public void execute() {
        board.processMoveExecution(this);
    }

    @Override
    public void undo() {
        piece.updatePosition(oldCol, oldRow);
        piece.isFirstMove = isFirstMove;

        if (targetPiece != null) {
            board.pieces.add(targetPiece);
        }
    }

    // TODO: Enpassant, Castling, Promotion

}
