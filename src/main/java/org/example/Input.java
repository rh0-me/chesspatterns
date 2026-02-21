package org.example;

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
        if (board.isPieceSelected()) {

            Point tile = mouseToTile(e);

            if (isOnBoard(tile)) {
                Move move = new Move(board, board.getSelectedPiece(), tile.x, tile.y);

                if (board.isValidMove(move)) {
                    board.makeMove(move);
                    board.repaint();
                }
            }
        }
        board.clearSelectedPiece();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged at: " + e.getX() + ", " + e.getY());

        if (board.isPieceSelected()) {

            // TODO: Check
            // Center the piece on the mouse cursor
            // This is a simple way to achieve the dragging effect, but it may not be perfect for all piece sizes or board configurations
            int x = e.getX() - board.tileSize / 2;
            int y = e.getY() - board.tileSize / 2;

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


