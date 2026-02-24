package org.example;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.command.Move;
import org.example.chesspatterns.model.pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }


    public boolean isInCheck(Move move) {


        return false; // King is safe
    }
}
