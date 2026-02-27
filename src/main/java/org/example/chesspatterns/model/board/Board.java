package org.example.chesspatterns.model.board;

import org.example.chesspatterns.model.pieces.*;
import org.example.chesspatterns.pattern.factory.PieceFactory;
import org.example.chesspatterns.pattern.factory.PieceType;
import org.example.chesspatterns.pattern.factory.StandardPieceFactory;
import org.example.chesspatterns.pattern.observer.BoardObserver;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<BoardObserver> observers = new ArrayList<>();
    private final Piece[][] grid = new Piece[8][8];
    private final PieceFactory pieceFactory;


    public Board() {
        this.pieceFactory = new StandardPieceFactory();
        setupBoard();
    }

    private void setupBoard() {
        for (int col = 0; col < 8; col++) {
            grid[6][col] = pieceFactory.createPiece(PieceType.PAWN, true); // Nutzung der Factory!
            grid[1][col] = pieceFactory.createPiece(PieceType.PAWN, false); // Schwarze Bauern


        }

        grid[7][0] = pieceFactory.createPiece(PieceType.ROOK, true);
        grid[7][1] = pieceFactory.createPiece(PieceType.KNIGHT, true);
        grid[7][2] = pieceFactory.createPiece(PieceType.BISHOP, true);
        grid[7][3] = pieceFactory.createPiece(PieceType.QUEEN, true);
        grid[7][4] = pieceFactory.createPiece(PieceType.KING, true);
        grid[7][5] = pieceFactory.createPiece(PieceType.BISHOP, true);
        grid[7][6] = pieceFactory.createPiece(PieceType.KNIGHT, true);
        grid[7][7] = pieceFactory.createPiece(PieceType.ROOK, true);

        grid[0][0] = pieceFactory.createPiece(PieceType.ROOK, false);
        grid[0][1] = pieceFactory.createPiece(PieceType.KNIGHT, false);
        grid[0][2] = pieceFactory.createPiece(PieceType.BISHOP, false);
        grid[0][3] = pieceFactory.createPiece(PieceType.QUEEN, false);
        grid[0][4] = pieceFactory.createPiece(PieceType.KING, false);
        grid[0][6] = pieceFactory.createPiece(PieceType.KNIGHT, false);
        grid[0][5] = pieceFactory.createPiece(PieceType.BISHOP, false);
        grid[0][7] = pieceFactory.createPiece(PieceType.ROOK, false);

    }

    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        grid[row][col] = piece;
    }

    public void addObserver(BoardObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (BoardObserver observer : observers) {
            observer.onBoardChanged();
        }
    }

    public void executeMove(/* Start, Ziel */) {
        notifyObservers();
    }

    public boolean isKingInCheck(boolean isWhiteKing) {
        int kingRow = -1;
        int kingCol = -1;

        // 1. Finde die Koordinaten des Königs
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p != null && p.isWhite() == isWhiteKing && p.getClass().getSimpleName().equals("King")) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }

        // Sicherheitsprüfung (falls der König z.B. in einem Test-Setup fehlt)
        if (kingRow == -1) return false;

        // 2. Prüfe alle Figuren auf dem Feld. Wenn es ein Gegner ist, frage seine Strategy!
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece enemy = getPiece(r, c);
                if (enemy != null && enemy.isWhite() != isWhiteKing) {
                    // Das Strategy Pattern leistet hier die ganze Arbeit!
                    if (enemy.canMove(this, r, c, kingRow, kingCol)) {
                        return true; // Ein Gegner kann den König schlagen -> SCHACH!
                    }
                }
            }
        }

        return false; // Kein Gegner kann den König erreichen
    }

    public boolean wouldMoveCauseCheck(int startRow, int startCol, int endRow, int endCol, boolean isWhite) {
        Piece movingPiece = getPiece(startRow, startCol);
        Piece targetPiece = getPiece(endRow, endCol);

        // 1. Simulation (heimlich verschieben)
        // Wir rufen absichtlich NICHT notifyObservers() auf, damit die GUI nicht flackert!
        grid[endRow][endCol] = movingPiece;
        grid[startRow][startCol] = null;

        // 2. Prüfung: Steht der König JETZT im Schach?
        boolean inCheck = isKingInCheck(isWhite);

        // 3. Rollback (alles wieder exakt so hinstellen wie vorher)
        grid[startRow][startCol] = movingPiece;
        grid[endRow][endCol] = targetPiece;

        return inCheck; // Gibt true zurück, wenn dieser Zug Selbstmord wäre
    }

    // Datei: Board.java (Ergänzung)

    public boolean hasAnyValidMoves(boolean isWhiteTurn) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = getPiece(r, c);

                // Nur die Figuren des aktuellen Spielers prüfen
                if (piece != null && piece.isWhite() == isWhiteTurn) {

                    // Alle 64 Felder als potenzielles Ziel testen
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {

                            // Wenn die Strategy den Zug erlaubt UND der Zug kein Schach verursacht...
                            if (piece.canMove(this, r, c, endRow, endCol)) {
                                if (!wouldMoveCauseCheck(r, c, endRow, endCol, isWhiteTurn)) {
                                    return true; // Wir haben mindestens einen rettenden Zug gefunden!
                                }
                            }

                        }
                    }
                }
            }
        }
        return false; // Kein einziger rettender Zug mehr möglich!
    }
}
