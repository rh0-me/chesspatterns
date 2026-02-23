package org.example.chesspatterns.model.board;

import org.example.CheckScanner;
import org.example.chesspatterns.model.pieces.*;
import org.example.chesspatterns.pattern.command.Command;
import org.example.chesspatterns.pattern.command.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Board {
    // horizontal 
    public final int boardWidthInTiles = 8;
    // vertical
    public final int boardHeightInTiles = 8;
    public Point enPassantTile = null;

    ArrayList<Piece> pieces = new ArrayList<>();
    CheckScanner checkScanner = new CheckScanner(this);

    private Piece selectedPiece;
    private Stack<Command> history = new Stack<>();

    public Board() {
        setStartingPosition();
    }

    public boolean isValidMove(Move move) {
        if (isSameTeam(move.piece, move.targetPiece)) return false;
        if (!move.piece.isValidMove(move.newCol, move.newRow)) return false;
        if (move.piece.moveCollidesWithPieces(move.newCol, move.newRow)) return false;
        if (checkScanner.isInCheck(move)) return false;

        return true;
    }

    public void makeMove(Move move) {
        move.execute();

        history.push(move);

    }

    public void capturePiece(Piece piece) {
        pieces.remove(piece);
    }

    public Piece getPieceAtLocation(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.column == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public void setSelectedPiece(Piece piece) {
        selectedPiece = piece;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public ArrayList<Point> getValidMovesForPiece(Piece piece) {
        ArrayList<Point> validMoves = new ArrayList<>();

        for (int row = 0; row < boardHeightInTiles; row++) {
            for (int col = 0; col < boardWidthInTiles; col++) {
                Move move = new Move(this, piece, col, row);
                if (isValidMove(move)) {
                    validMoves.add(new Point(col, row));
                }
            }
        }

        return validMoves;
    }

    public void setStartingPosition() {
        pieces.clear();

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

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public boolean isSameTeam(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return false;
        }
        return piece1.isWhite == piece2.isWhite;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
