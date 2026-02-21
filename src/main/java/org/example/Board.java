package org.example;

import org.example.models.*;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 50;

    // horizontal 
    int rankCount = 8;

    // vertical
    int fileCount = 8;

    public Board() {
        this.setPreferredSize(new Dimension(rankCount * tileSize, fileCount * tileSize));

        setStartingPosition();
    }

    ArrayList<Piece> pieces = new ArrayList<>();


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < rankCount; row++) {
            for (int column = 0; column < fileCount; column++) {
                if ((row + column) % 2 == 0) {
                    g.setColor(new Color(245, 222, 170));
                } else {
                    g.setColor(new Color(120, 80, 50));
                }
                g.fillRect(row * tileSize, column * tileSize, tileSize, tileSize);
            }
        }


        for (Piece piece : pieces) {
            piece.paint((Graphics2D) g);
        }
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
