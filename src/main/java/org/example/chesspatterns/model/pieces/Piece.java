package org.example.chesspatterns.model.pieces;

import org.example.chesspatterns.model.board.Board;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class Piece {

    public int row, column;
    public int xPos, yPos;

    public boolean isWhite;
    public boolean isFirstMove = true;


    Image sprite;

    Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public Piece(Board board, int col, int row, boolean isWhite) {
        this.board = board;
        this.row = row;
        this.column = col;
        this.isWhite = isWhite;

        this.xPos = this.column * board.tileSize;
        this.yPos = this.row * board.tileSize;
    }


    public void updatePosition(int col, int row) {
        this.column = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
    }

    public Image getSprite(String spriteName) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(spriteName)) {
            if (inputStream == null)
                throw new IllegalStateException("Missing sprite resource" + spriteName);

            Image img = javax.imageio.ImageIO.read(inputStream);
            if (img == null)
                throw new IllegalStateException("Sprite file could not be decoded as an image: " + spriteName);

            return img.getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load sprite resource: " + spriteName, e);
        }
    }

    public boolean isValidMove(int targetCol, int targetRow) {
        // Basic bounds check
        if (targetCol < 0 || targetCol >= board.boardWidthInTiles || targetRow < 0 || targetRow >= board.boardHeightInTiles) {
            return false;
        }

        // Check if the target square is occupied by a piece of the same color
        Piece targetPiece = board.getPieceAtLocation(targetCol, targetRow);
        if (targetPiece != null && targetPiece.isWhite == this.isWhite) {
            return false;
        }

        return true; // Default to valid move, specific piece types will override this
    }

    public boolean moveCollidesWithPieces(int targetCol, int targetRow) {
        // This method can be overridden by pieces that need to check for collisions (like Rooks, Bishops, Queens)
        return false;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
