package org.example.chesspatterns.pattern.command;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> commandHistory = new Stack<>();

    // Führt einen Befehl aus und legt ihn auf den Stapel
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }

    // Macht den letzten Befehl rückgängig
    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("Keine Züge zum Rückgängigmachen vorhanden.");
        }
    }
}
