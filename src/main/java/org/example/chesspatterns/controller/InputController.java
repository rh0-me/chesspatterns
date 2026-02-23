package org.example.chesspatterns.controller;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.command.Move;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.view.BoardPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputController extends MouseAdapter {

    private final Board boardModel;
    private final BoardPanel boardView;

    public InputController(Board boardModel, BoardPanel boardView) {
        this.boardModel = boardModel;
        this.boardView = boardView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / boardView.tileSize;
        int row = e.getY() / boardView.tileSize;

        Piece piece = boardModel.getPieceAtLocation(col, row);
        if (piece != null) {
            boardModel.setSelectedPiece(piece);
            boardView.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / boardView.tileSize;
        int row = e.getY() / boardView.tileSize;

        Piece selectedPiece = boardModel.getSelectedPiece();

        if (selectedPiece != null) {
            Move move = new Move(boardModel, selectedPiece, col, row);

            if (boardModel.isValidMove(move)) {
                boardModel.makeMove(move);
            } else {
                // rest dragging effect
                selectedPiece.xPos = selectedPiece.column * boardView.tileSize;
                selectedPiece.yPos = selectedPiece.row * boardView.tileSize;
            }

            boardModel.setSelectedPiece(null);
            boardView.repaint(); // Neu zeichnen lassen
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        Piece selectedPiece = boardModel.getSelectedPiece();
        // Dragging effect
        if (selectedPiece != null) {
            selectedPiece.xPos = e.getX() - boardView.tileSize / 2;
            selectedPiece.yPos = e.getY() - boardView.tileSize / 2;

            boardView.repaint();
        }
    }
}


