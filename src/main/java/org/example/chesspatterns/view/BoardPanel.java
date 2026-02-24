package org.example.chesspatterns.view;


import org.example.chesspatterns.controller.InputController;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.observer.GameObserver;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BoardPanel extends JPanel implements GameObserver {
    private final Board boardModel;

    public final int tileSize = 100;
    private final Map<String, Image> imageCache = new HashMap<>();

    // NEW: Temporary Visual State for Dragging
    private int dragX = -1;
    private int dragY = -1;

    public BoardPanel(Board boardModel) {
        this.boardModel = boardModel;
        InputController inputController = new InputController(boardModel, this);

        this.addMouseListener(inputController);
        this.addMouseMotionListener(inputController);

        int width = boardModel.boardWidthInTiles * tileSize;
        int height = boardModel.boardHeightInTiles * tileSize;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.GREEN);

    }

    public void setDragPosition(int x, int y) {
        this.dragX = x;
        this.dragY = y;
        repaint(); // Trigger a redraw
    }

    public void clearDragPosition() {
        this.dragX = -1;
        this.dragY = -1;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        paintBoard(g2);
        paintHighlights(g2);

        for (Piece piece : boardModel.getPieces()) {
            paintPiece(g2, piece);
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

    private void paintPiece(Graphics2D g2d, Piece piece) {

        Image img = getImage(piece.getImageName());
        int x, y;

        // OPTIONAL: If this piece is currently being dragged by the user,
        // use the drag coordinates from the Controller instead of the grid coordinates.
        // (You would need to pass the currently dragged piece from InputController to here)
        if (piece == boardModel.getSelectedPiece() && dragX != -1 && dragY != -1) {
            x = dragX;
            y = dragY;
        } else {

            // Calculate Pixel Position based on Logical Position
            x = piece.column * tileSize;
            y = piece.row * tileSize;
        }


        g2d.drawImage(img, x, y, tileSize, tileSize, null);

    }


    private Image getImage(String imageName) {
        // If we already loaded it, return it from cache
        if (imageCache.containsKey(imageName)) {
            return imageCache.get(imageName);
        }

        // Otherwise load it
        try (InputStream is = ClassLoader.getSystemResourceAsStream(imageName)) {
            if (is == null) throw new IOException("Resource not found: " + imageName);
            Image img = javax.imageio.ImageIO.read(is);
            imageCache.put(imageName, img); // Save to cache
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update() {
        this.repaint();
        this.revalidate();
    }
}
