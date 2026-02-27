package org.example.chesspatterns.pattern.command;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> commandHistory = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("No commands to undo.");
        }
    }

    public boolean hasHistory() {
        return !commandHistory.isEmpty();
    }

    public void clearHistory() {
        commandHistory.clear();
    }
}
