package org.example.chesspatterns.core;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.model.pieces.PieceType;
import org.example.chesspatterns.pattern.command.Move;
import org.example.chesspatterns.pattern.memento.BoardMemento;
import org.example.chesspatterns.pattern.state.GameOverState;
import org.example.chesspatterns.pattern.state.GameState;
import org.example.chesspatterns.pattern.state.WhiteTurnState;

public class GameManager {

    private static GameManager instance;
    public GameState currentState;
    private PromotionHandler promotionHandler;
    public Board board;

    private GameManager() {
    }

    public BoardMemento quickSaveSlot = null;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void setPromotionHandler(PromotionHandler promotionHandler) {
        this.promotionHandler = promotionHandler;
    }

    public PieceType askPromotionChoice(boolean isWhite) {
        if (promotionHandler != null) {
            return promotionHandler.handlePromotion(isWhite);
        }
        return PieceType.QUEEN; // Default to Queen if no handler is set
    }

    public void initializeGame(Board board) {
        this.board = board;
        setState(new WhiteTurnState());
    }

    public boolean isWhiteTurn() {
        return currentState instanceof WhiteTurnState;
    }

    public void nextTurn() {
        currentState.nextTurn(this);
        checkGameStatusForCurrentPlayer();
    }

    public void checkGameStatusForCurrentPlayer() {
        boolean isWhiteToMove = isWhiteTurn();
        boolean inCheck = board.checkScanner.isKingInCheck(isWhiteToMove);
        boolean anyMovePossible = hasAnyValidMoves(isWhiteToMove);

        if (!anyMovePossible) {
            //Win
            if (inCheck) {
                String winner = isWhiteToMove ? "Black" : "White";
                setState(new GameOverState("Checkmate - " + winner + " wins"));
                javax.swing.JOptionPane.showMessageDialog(null, "SCHACHMATT! Spiel vorbei.");
            }
            // Stalemate
            else {
                setState(new GameOverState("Stalemate"));
                System.out.println("PATT! Unentschieden.");
                javax.swing.JOptionPane.showMessageDialog(null, "Patt! Unentschieden.");
            }
        }
    }

    private boolean hasAnyValidMoves(boolean isWhiteToMove) {
        for (Piece p : board.getPieces()) {
            if (p.isWhite == isWhiteToMove) {
                // Probiere JEDES Feld auf dem Brett
                for (int r = 0; r < 8; r++) {
                    for (int c = 0; c < 8; c++) {
                        Move m = new Move(board, p, c, r);
                        if (board.isValidMove(m)) {
                            return true; // Es gibt mindestens einen Rettungsweg
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
