package org.example;

import org.example.models.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 100;

    CheckScanner checkScanner = new CheckScanner(this);

    // horizontal 
    public final int boardWidthInTiles = 8;

    // vertical
    public final int boardHeightInTiles = 8;

    public Point enPassantTile = null;

    public void undoMove() {

    }

    public void resetBoard() {
        enPassantTile = null;
        clearSelectedPiece();
        pieces.clear();
        setStartingPosition();
        repaint();
    }

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
        if (isSameTeam(move.piece, move.targetPiece)) return false;

        if (!move.piece.isValidMove(move.newCol, move.newRow)) {
            return false;
        }

        if (move.piece.moveCollidesWithPieces(move.newCol, move.newRow)) return false;

        if (checkScanner.isInCheck(move)) return false;

        return true;
    }

    public boolean isValidMoveWithOutCheck(Move move) {
        if (isSameTeam(move.piece, move.targetPiece)) return false;

        if (!move.piece.isValidMove(move.newCol, move.newRow)) {
            return false;
        }

        if (move.piece.moveCollidesWithPieces(move.newCol, move.newRow)) return false;

        return true;
    }

    public void makeMove(Move move) {

        boolean isDoubleStepPawnMove = move.piece instanceof Pawn && move.piece.isFirstMove && Math.abs(move.newRow - move.piece.row) == 2;


        if (move.piece instanceof Pawn) {
            int direction = move.piece.isWhite ? -1 : 1;

            boolean isEnPassantMove =
                    enPassantTile != null
                            && move.targetPiece == null
                            && move.newCol == enPassantTile.x
                            && move.newRow == enPassantTile.y
                            && Math.abs(move.oldCol - move.newCol) == 1
                            && move.piece.row == enPassantTile.y - direction;

            if (isEnPassantMove) {
                Piece capturedPawn = getPieceAtLocation(enPassantTile.x, enPassantTile.y - direction);
                if (capturedPawn instanceof Pawn) {
                    capturePiece(capturedPawn);
                }
            }
        }

        if (!isDoubleStepPawnMove) {
            enPassantTile = null;
        }

        move.piece.updatePosition(move.newCol, move.newRow);
        move.piece.isFirstMove = false;

        if (move.targetPiece != null) {
            capturePiece(move.targetPiece);
        }

        // Handle promotion
        if ((move.newRow == 0 && move.piece.isWhite) || (move.newRow == boardHeightInTiles - 1 && !move.piece.isWhite)) {
            promotePawn(move);
        }
    }

    private void promotePawn(Move move) {
        Piece pawn = move.piece;
        capturePiece(pawn);

        // For simplicity auto-promote to a Queen
        Piece promoted = new Queen(this, move.newCol, move.newRow, pawn.isWhite);
        addPiece(promoted);

        move.piece = promoted;
    }

    public void capturePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void clearSelectedPiece() {
        selectedPiece = null;
    }

    Piece findKing(boolean isWhite) {
        for (Piece piece : pieces) {
            if (piece instanceof King && piece.isWhite == isWhite) {
                return piece;
            }
        }
        return null;
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
//            paintHighlightEnPassant(g2);

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

                        // translucent fill
                        g2d.setColor(new Color(0, 248, 0, 100));
                        g2d.fillRect(x, y, tileSize, tileSize);
                    }
                }
            }

        }
    }

    private void paintHighlightEnPassant(Graphics2D g2d) {
        if (enPassantTile != null) {
            int x = enPassantTile.x * tileSize;
            int y = enPassantTile.y * tileSize;

            // translucent fill
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fillRect(x, y, tileSize, tileSize);
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
                g2d.fillRect(column * tileSize, row * tileSize, tileSize, tileSize);
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
