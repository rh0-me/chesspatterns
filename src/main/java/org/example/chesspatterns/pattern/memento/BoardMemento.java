package org.example.chesspatterns.pattern.memento;

import org.example.chesspatterns.model.pieces.Piece;

import java.util.List;

public class BoardMemento {

    private final List<Piece> piecesSnapshot;

    public BoardMemento(List<Piece> pieces) {
        this.piecesSnapshot = pieces;
    }

    public List<Piece> getPiecesSnapshot() {
        return piecesSnapshot;
    }
}
