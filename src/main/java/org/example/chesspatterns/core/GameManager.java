package org.example.chesspatterns.core;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.command.CommandManager;
import org.example.chesspatterns.pattern.factory.PieceFactory;
import org.example.chesspatterns.pattern.factory.StandardPieceFactory;
import org.example.chesspatterns.pattern.state.BlackTurnState;
import org.example.chesspatterns.pattern.state.GameState;
import org.example.chesspatterns.pattern.state.WhiteTurnState;

//public class GameManager {
//
//    private static GameManager instance;
//    public GameState currentState;
//    private PromotionHandler promotionHandler;
//    public Board board;
//
//    private GameManager() {
//    }
//
//    public BoardMemento quickSaveSlot = null;
//
//    public static GameManager getInstance() {
//        if (instance == null) {
//            instance = new GameManager();
//        }
//        return instance;
//    }
//
//    public void setPromotionHandler(PromotionHandler promotionHandler) {
//        this.promotionHandler = promotionHandler;
//    }
//
//    public PieceType askPromotionChoice(boolean isWhite) {
//        if (promotionHandler != null) {
//            return promotionHandler.handlePromotion(isWhite);
//        }
//        return PieceType.QUEEN; // Default to Queen if no handler is set
//    }
//
//    public void initializeGame(Board board) {
//        this.board = board;
//        setState(new WhiteTurnState());
//    }
//
//    public boolean isWhiteTurn() {
//        return currentState instanceof WhiteTurnState;
//    }
//
//    public void nextTurn() {
//        currentState.nextTurn(this);
//        checkGameStatusForCurrentPlayer();
//    }
//
//    public void checkGameStatusForCurrentPlayer() {
//        boolean isWhiteToMove = isWhiteTurn();
//        boolean inCheck = board.checkScanner.isKingInCheck(isWhiteToMove);
//        boolean anyMovePossible = hasAnyValidMoves(isWhiteToMove);
//
//        if (!anyMovePossible) {
//            //Win
//            if (inCheck) {
//                String winner = isWhiteToMove ? "Black" : "White";
//                setState(new GameOverState("Checkmate - " + winner + " wins"));
//                javax.swing.JOptionPane.showMessageDialog(null, "SCHACHMATT! Spiel vorbei.");
//            }
//            // Stalemate
//            else {
//                setState(new GameOverState("Stalemate"));
//                System.out.println("PATT! Unentschieden.");
//                javax.swing.JOptionPane.showMessageDialog(null, "Patt! Unentschieden.");
//            }
//        }
//    }
//
//    private boolean hasAnyValidMoves(boolean isWhiteToMove) {
//        for (Piece p : board.getPieces()) {
//            if (p.isWhite == isWhiteToMove) {
//                // Probiere JEDES Feld auf dem Brett
//                for (int r = 0; r < 8; r++) {
//                    for (int c = 0; c < 8; c++) {
//                        Move m = new Move(board, p, c, r);
//                        if (board.isValidMove(m)) {
//                            return true; // Es gibt mindestens einen Rettungsweg
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    public void setState(GameState state) {
//        this.currentState = state;
//    }
//
//    public GameState getCurrentState() {
//        return currentState;
//    }
//}


public class GameManager {
    private static GameManager instance;
    private GameState currentState;
    private final Board board;
    private final CommandManager commandManager;
    private final PieceFactory pieceFactory;

    private GameManager() {
        this.board = new Board();
        this.commandManager = new CommandManager();
        // Weiß beginnt traditionell
        this.currentState = new WhiteTurnState();
        this.pieceFactory = new StandardPieceFactory();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public PieceFactory getPieceFactory() {
        return pieceFactory;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
        System.out.println("Zustand gewechselt zu: " + newState.getStateName());
    }

    public Board getBoard() {
        return board;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    // Die GUI ruft diese Methode auf, wenn der Spieler klickt.
    // Der GameManager leitet die Anfrage blind an den aktuellen Zustand weiter!
    public boolean attemptMove(int startRow, int startCol, int endRow, int endCol) {
        return currentState.handleMove(this, startRow, startCol, endRow, endCol);
    }

    public void undo() {
        commandManager.undoLastCommand();
        // Wenn man Undo macht, muss man theoretisch auch den State zurücksetzen.
        // Der Einfachheit halber tauschen wir hier einfach den State aus:
        if (currentState instanceof WhiteTurnState) {
            setState(new BlackTurnState());
        } else if (currentState instanceof BlackTurnState) {
            setState(new WhiteTurnState());
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }
}