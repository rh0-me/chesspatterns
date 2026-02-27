package org.example.chesspatterns.core;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.command.CommandManager;
import org.example.chesspatterns.pattern.factory.PieceFactory;
import org.example.chesspatterns.pattern.factory.StandardPieceFactory;
import org.example.chesspatterns.pattern.observer.GameStateObserver;
import org.example.chesspatterns.pattern.state.BlackTurnState;
import org.example.chesspatterns.pattern.state.GameState;
import org.example.chesspatterns.pattern.state.WhiteTurnState;

import java.util.List;


public class GameManager {
    private static GameManager instance;
    private GameState currentState;
    private final Board board;
    private final CommandManager commandManager;
    private final PieceFactory pieceFactory;
    private final List<GameStateObserver> observers = new java.util.ArrayList<>();

    private GameManager() {
        this.board = new Board();
        this.commandManager = new CommandManager();

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

    public void addStateObserver(GameStateObserver observer) {
        observers.add(observer);

        if (currentState != null) {
            observer.onStateChanged(currentState);
        }
    }

    public void notifyStateObservers() {
        for (GameStateObserver observer : observers) {
            observer.onStateChanged(currentState);
        }
    }


    public void setState(GameState newState) {
        this.currentState = newState;
        System.out.println("State changed to: " + newState.getStateName());
        notifyStateObservers();
    }

    public Board getBoard() {
        return board;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    // UI induced move attempt
    // Redirect to current state
    public boolean attemptMove(int startRow, int startCol, int endRow, int endCol) {
        return currentState.handleMove(this, startRow, startCol, endRow, endCol);
    }

    public void undo() {
        if (commandManager.hasHistory()) {

            commandManager.undoLastCommand();
            // Simplified state toggle
            if (currentState instanceof WhiteTurnState) {
                setState(new BlackTurnState());
            } else if (currentState instanceof BlackTurnState) {
                setState(new WhiteTurnState());
            }
        } else {
            System.out.println("No moves to undo");
        }
    }


    public void resetGame() {
        board.resetBoard();
        commandManager.clearHistory();
        setState(new WhiteTurnState());
    }

    public GameState getCurrentState() {
        return currentState;
    }
}