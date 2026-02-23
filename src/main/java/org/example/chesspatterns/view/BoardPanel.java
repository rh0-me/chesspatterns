package org.example.chesspatterns.view;


import org.example.chesspatterns.controller.InputController;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private Board boardModel;
    private InputController inputController;

    public final int tileSize = 100;

    public BoardPanel(Board boardModel) {
        this.boardModel = boardModel;
        this.inputController = new InputController(boardModel, this);

        this.addMouseListener(inputController);
        this.addMouseMotionListener(inputController);

        int width = boardModel.boardWidthInTiles * tileSize;
        int height = boardModel.boardHeightInTiles * tileSize;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.GREEN);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {

            paintBoard(g2);
            paintHighlights(g2);
            paintPieces(g2);

        } finally {
            g2.dispose();
        }
    }


    private void paintBoard(Graphics2D g2d) {
        for (int row = 0; row < boardModel.boardHeightInTiles; row++) {
            for (int column = 0; column < boardModel.boardWidthInTiles; column++) {
                if ((row + column) % 2 == 0) {
                    g2d.setColor(new Color(245, 222, 170));
                } else {
                    g2d.setColor(new Color(120, 80, 50));
                }
                g2d.fillRect(column * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    private void paintHighlights(Graphics2D g2) {
        paintHighlightedTile(g2);
        paintHighlightPossibleMoves(g2);
//      paintHighlightEnPassant(g2);
    }

    private void paintHighlightedTile(Graphics2D g2d) {
        Piece selectedPiece = boardModel.getSelectedPiece();
        if (selectedPiece != null) {
            int x = selectedPiece.column * tileSize;
            int y = selectedPiece.row * tileSize;

            g2d.setColor(new Color(236, 236, 43, 181));
            g2d.fillRect(x, y, tileSize, tileSize);
        }
    }

    private void paintHighlightPossibleMoves(Graphics2D g2d) {
        Piece selectedPiece = boardModel.getSelectedPiece();
        if (selectedPiece != null) {
            for (Point point : boardModel.getValidMovesForPiece(selectedPiece)) {

                int x = point.x * tileSize;
                int y = point.y * tileSize;

                g2d.setColor(new Color(0, 248, 0, 100));
                g2d.fillRect(x, y, tileSize, tileSize);
            }
        }
    }

    private void paintHighlightEnPassant(Graphics2D g2d) {
        if (boardModel.enPassantTile != null) {
            int x = boardModel.enPassantTile.x * tileSize;
            int y = boardModel.enPassantTile.y * tileSize;

            // translucent fill
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fillRect(x, y, tileSize, tileSize);
        }
    }

    private void paintPieces(Graphics2D g2d) {
        for (Piece piece : boardModel.getPieces()) {
            piece.paint(g2d);
        }
    }

}
