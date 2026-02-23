package org.example.models;

import org.example.Board;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class Piece {

    public int row, column;
    public int xPos, yPos;

    public boolean isWhite;


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

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
