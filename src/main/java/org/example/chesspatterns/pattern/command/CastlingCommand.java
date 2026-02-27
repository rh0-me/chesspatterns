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

        // King or queen side castling
        if (kingEndCol > kingStartCol) {
            this.rookStartCol = 7;
            this.rookEndCol = kingEndCol - 1;
        } else { 
            this.rookStartCol = 0;
            this.rookEndCol = kingEndCol + 1; 
        }
    }

    private boolean kingHadMoved;
    private boolean rookHadMoved;

    @Override
    public void execute() {
        king = board.getPiece(row, kingStartCol);
        rook = board.getPiece(row, rookStartCol);

        kingHadMoved = king.hasMoved();
        rookHadMoved = rook.hasMoved();

        board.setPiece(row, kingEndCol, king);
        board.setPiece(row, kingStartCol, null);
        king.setHasMoved(true);

        board.setPiece(row, rookEndCol, rook);
        board.setPiece(row, rookStartCol, null);
        rook.setHasMoved(true);

        board.notifyObservers();
    }

    @Override
    public void undo() {
        board.setPiece(row, kingStartCol, king);
        board.setPiece(row, kingEndCol, null);
        king.setHasMoved(kingHadMoved);

        board.setPiece(row, rookStartCol, rook);
        board.setPiece(row, rookEndCol, null);
        rook.setHasMoved(rookHadMoved);

        board.notifyObservers();
    }
}
