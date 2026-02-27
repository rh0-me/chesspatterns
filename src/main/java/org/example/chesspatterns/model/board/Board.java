package org.example.chesspatterns.model.board;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.CheckScanner;
import org.example.chesspatterns.model.pieces.*;
import org.example.chesspatterns.pattern.command.Command;
import org.example.chesspatterns.pattern.command.Move;
import org.example.chesspatterns.pattern.observer.GameObserver;

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
    public CheckScanner checkScanner = new CheckScanner(this);

    private Piece selectedPiece;
    private final Stack<Command> history = new Stack<>();
    private final List<GameObserver> observers = new ArrayList<>();
    private final PieceFactory pieceFactory = new PieceFactory();

    public Board() {
    }

    public boolean isValidMove(Move move) {
        if (isSameTeam(move.piece, move.getCapturedPiece())) return false;
        if (!move.piece.isValidMove(move.newCol, move.newRow)) return false;
        if (move.piece.moveCollidesWithPieces(move.newCol, move.newRow)) return false;

        move.execute();
        boolean kingInCheckAfterMove = checkScanner.isKingInCheck(move.piece.isWhite);
        move.undo();

        if (kingInCheckAfterMove)
            return false;

        return true;
    }

    public void makeMove(Move move) {
        move.execute();
        history.push(move);
        notifyObservers();
        GameManager.getInstance().nextTurn();

    }

    public void undoMove() {
        if (!history.empty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
            GameManager.getInstance().nextTurn();
        }
    }

    public void resetBoard() {
        pieces.clear();
        history.clear();
        enPassantTile = null;
        selectedPiece = null;

        setStartingPosition();
    }

    public void setStartingPosition() {

        addPiece(pieceFactory.createPiece(PieceType.ROOK, this, 0, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.KNIGHT, this, 1, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.BISHOP, this, 2, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.QUEEN, this, 3, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.KING, this, 4, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.BISHOP, this, 5, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.KNIGHT, this, 6, 0, false));
        addPiece(pieceFactory.createPiece(PieceType.ROOK, this, 7, 0, false));

        for (int i = 0; i < 8; i++) {
            addPiece(pieceFactory.createPiece(PieceType.PAWN, this, i, 1, false));
        }

        addPiece(pieceFactory.createPiece(PieceType.ROOK, this, 0, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.KNIGHT, this, 1, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.BISHOP, this, 2, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.QUEEN, this, 3, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.KING, this, 4, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.BISHOP, this, 5, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.KNIGHT, this, 6, 7, true));
        addPiece(pieceFactory.createPiece(PieceType.ROOK, this, 7, 7, true));

        for (int i = 0; i < 8; i++) {
            addPiece(pieceFactory.createPiece(PieceType.PAWN, this, i, 6, true));
        }
    }

    public boolean isSameTeam(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) {
            return false;
        }
        return piece1.isWhite == piece2.isWhite;
    }

    public Piece findKing(boolean isWhite) {
        for (Piece piece : pieces) {
            if (piece instanceof King && piece.isWhite == isWhite) {
                return piece;
            }
        }
        return null;
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

    public void setSelectedPiece(Piece piece) {
        selectedPiece = piece;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public Piece promotePiece(PieceType type, boolean isWhite, int newCol, int newRow) {

        return pieceFactory.createPiece(type, this, newCol, newRow, isWhite);
    }


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
}
