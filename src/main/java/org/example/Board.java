package org.example;

import org.example.models.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 100;

    // horizontal 
    public final int boardWidthInTiles = 8;

    // vertical
    public final int boardHeightInTiles = 8;

    Input input = new Input(this);

    ArrayList<Piece> pieces = new ArrayList<>();
    private Piece selectedPiece;

    public Board() {
        this.setPreferredSize(new Dimension(boardWidthInTiles * tileSize, boardHeightInTiles * tileSize));

        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        setStartingPosition();
    }


    public boolean isValidMove(Move move) {
        if (isSameTeam(move.piece, move.targetPiece))
            return false;

        if (!move.piece.isValidMove(move.newCol, move.newRow)) {
            return false;
        }

        if (move.piece.moveCollidesWithPieces(move.newCol, move.newRow))
            return false;

        return true;
    }

    public void makeMove(Move move) {
        move.piece.column = move.newCol;
        move.piece.row = move.newRow;

        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;

        if (move.targetPiece != null) {
            capturePiece(move.targetPiece);
        }
    }

    public void capturePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void clearSelectedPiece() {
        selectedPiece = null;
    }

    public void tryMoveSelectedPieceTo(int col, int row) {
        if (selectedPiece != null) {
            Move move = new Move(this, selectedPiece, col, row);
            if (isValidMove(move)) {
                makeMove(move);
            } else {
                selectedPiece.xPos = selectedPiece.column * tileSize;
                selectedPiece.yPos = selectedPiece.row * tileSize;
            }
            clearSelectedPiece();
            repaint();
        }
    }

    public boolean isPieceSelected() {
        return selectedPiece != null;
    }

    public void selectAt(int col, int row) {
        Piece piece = getPieceAtLocation(col, row);
        if (piece != null) {
            System.out.println("Selected piece " + piece.getClass() + " col: " + col + ", row: " + row);
            selectedPiece = piece;
            repaint();
        }
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isSameTeam(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return false;
        }
        return piece1.isWhite == piece2.isWhite;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {

            paintBoard(g2);
            paintHighlightedTile(g2);
            paintHighlightPossibleMoves(g2);
            paintPieces(g2);

        } finally {
            g2.dispose();
        }
    }

    private void paintHighlightedTile(Graphics2D g2d) {
        if (selectedPiece != null) {
            int x = selectedPiece.column * tileSize;
            int y = selectedPiece.row * tileSize;

            // translucent fill
            g2d.setColor(new Color(236, 236, 43, 181));
            g2d.fillRect(x, y, tileSize, tileSize);
        }
    }

    private void paintHighlightPossibleMoves(Graphics2D g2d) {
        if (selectedPiece != null) {
            for (int row = 0; row < boardHeightInTiles; row++) {
                for (int column = 0; column < boardWidthInTiles; column++) {
                    Move move = new Move(this, selectedPiece, column, row);
                    if (isValidMove(move)) {
                        int x = column * tileSize;
                        int y = row * tileSize;

                        int dotSize = tileSize / 5;
                        int dotX = x + (tileSize / 2) - (dotSize / 2);
                        int dotY = y + (tileSize / 2) - (dotSize / 2);


                        // translucent fill
                        g2d.setColor(new Color(78, 81, 78, 181));
                        g2d.fillOval(dotX, dotY, dotSize, dotSize);
                    }
                }
            }

        }
    }

    private void paintPieces(Graphics2D g2d) {
        for (Piece piece : pieces) {
            piece.paint(g2d);
        }
    }

    private void paintBoard(Graphics2D g2d) {
        for (int row = 0; row < boardHeightInTiles; row++) {
            for (int column = 0; column < boardWidthInTiles; column++) {
                if ((row + column) % 2 == 0) {
                    g2d.setColor(new Color(245, 222, 170));
                } else {
                    g2d.setColor(new Color(120, 80, 50));
                }
                g2d.fillRect(row * tileSize, column * tileSize, tileSize, tileSize);
            }
        }
    }

    public Piece getPieceAtLocation(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.column == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void setStartingPosition() {
        addPiece(new Rook(this, 0, 0, false));
        addPiece(new Knight(this, 1, 0, false));
        addPiece(new Bishop(this, 2, 0, false));
        addPiece(new Queen(this, 3, 0, false));
        addPiece(new King(this, 4, 0, false));
        addPiece(new Bishop(this, 5, 0, false));
        addPiece(new Knight(this, 6, 0, false));
        addPiece(new Rook(this, 7, 0, false));

        for (int i = 0; i < 8; i++) {
            addPiece(new Pawn(this, i, 1, false));
        }

        addPiece(new Rook(this, 0, 7, true));
        addPiece(new Knight(this, 1, 7, true));
        addPiece(new Bishop(this, 2, 7, true));
        addPiece(new Queen(this, 3, 7, true));
        addPiece(new King(this, 4, 7, true));
        addPiece(new Bishop(this, 5, 7, true));
        addPiece(new Knight(this, 6, 7, true));
        addPiece(new Rook(this, 7, 7, true));

        for (int i = 0; i < 8; i++) {
            addPiece(new Pawn(this, i, 6, true));
        }
    }

}
