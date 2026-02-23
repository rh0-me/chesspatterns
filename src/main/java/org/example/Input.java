package org.example;

import org.example.models.Piece;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter {

    private final Board board;

    public Input(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at: " + e.getX() + ", " + e.getY());
        Point tile = mouseToTile(e);

        if (isOnBoard(tile)) {
            board.selectAt(tile.x, tile.y);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released at: " + e.getX() + ", " + e.getY());
        Point tile = mouseToTile(e);
        Piece selectedPiece = board.getSelectedPiece();
        if (selectedPiece != null) {
            if (!isOnBoard(tile)) {
                selectedPiece.xPos = selectedPiece.column * board.tileSize;
                selectedPiece.yPos = selectedPiece.row * board.tileSize;
                board.clearSelectedPiece();
                board.repaint();
                return;
            }
            Move move = new Move(board, selectedPiece, tile.x, tile.y);

            if (board.isValidMove(move)) {
                board.makeMove(move);
                board.clearSelectedPiece();
            }
            // If move is invalid, snap piece back to its original position
            else {
                selectedPiece.xPos = selectedPiece.column * board.tileSize;
                selectedPiece.yPos = selectedPiece.row * board.tileSize;
            }
            board.repaint();
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged at: " + e.getX() + ", " + e.getY());

        Piece selectedPiece = board.getSelectedPiece();

        // Dragging effect
        if (selectedPiece != null) {
            selectedPiece.xPos = e.getX() - board.tileSize / 2;
            selectedPiece.yPos = e.getY() - board.tileSize / 2;

            board.repaint();
        }
    }

    private Point mouseToTile(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        return new Point(col, row);
    }

    private boolean isOnBoard(Point tile) {
        return tile.x >= 0 && tile.x < board.boardWidthInTiles
                && tile.y >= 0 && tile.y < board.boardHeightInTiles;
    }
}


