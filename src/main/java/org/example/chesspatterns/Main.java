package org.example.chesspatterns;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.pattern.flyweight.ImageCache;
import org.example.chesspatterns.pattern.observer.GameStateObserver;
import org.example.chesspatterns.pattern.state.BlackTurnState;
import org.example.chesspatterns.pattern.state.GameState;
import org.example.chesspatterns.view.BoardView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess Patterns");
            frame.setIconImage(ImageCache.getInstance().getIcon());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(1000, 1000));
            positionFrameOnScreen(frame, 1);


            GameManager gm = GameManager.getInstance();
            BoardView boardView = new BoardView(gm.getBoard());

            JPanel controlPanel = createControlPanel(frame, gm, boardView);

            frame.add(controlPanel, BorderLayout.SOUTH);
            frame.add(boardView);
            frame.setVisible(true);
        });
    }


    // helper method to position the frame on a specific screen 
    private static void positionFrameOnScreen(JFrame frame, int screenIndex) {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        if (screens.length > screenIndex) {
            Rectangle bounds = screens[screenIndex].getDefaultConfiguration().getBounds();
            int x = bounds.x + (bounds.width - frame.getWidth()) / 2;
            int y = bounds.y + (bounds.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);
        } else {
            frame.setLocationRelativeTo(null);
        }
    }

    private static JPanel createControlPanel(JFrame frame, GameManager manager, BoardView boardView) {
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(Color.DARK_GRAY);


        JPanel buttonPanel = createButtonPanel(frame, manager, boardView);
        JPanel turnPanel = createTurnPanel(manager);

        controlPanel.add(BorderLayout.WEST, buttonPanel);
        controlPanel.add(BorderLayout.EAST, turnPanel);

        return controlPanel;
    }

    private static JPanel createButtonPanel(JFrame frame, GameManager manager, BoardView boardView) {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setOpaque(false);


        Dimension buttonSize = new Dimension(80, 80);
        JButton undoButton = new JButton(ImageCache.getInstance().getScaledImage("undo", 50, 50));
        undoButton.setPreferredSize(buttonSize);
        JButton resetButton = new JButton(ImageCache.getInstance().getScaledImage("restart", 50, 50));
        resetButton.setPreferredSize(buttonSize);

        buttonPanel.add(resetButton);
        buttonPanel.add(undoButton);

        undoButton.addActionListener(_ -> {
            manager.undo();
            boardView.requestFocus();
        });

        resetButton.addActionListener(_ -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you really want to restart the game?",
                    "Restart",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                manager.resetGame();
            }
            boardView.requestFocus();
        });

        return buttonPanel;
    }

    private static JPanel createTurnPanel(GameManager manager) {

        JPanel turnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        turnPanel.setOpaque(false);


        JLabel turnTextLabel = new JLabel();
        turnTextLabel.setForeground(Color.WHITE);
        turnTextLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel turnLabel = new JLabel();
        turnLabel.setPreferredSize(new Dimension(80, 80));
        turnLabel.setOpaque(true);
        turnLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        turnPanel.add(turnTextLabel);
        turnPanel.add(turnLabel);

        manager.addStateObserver(state -> {
            turnTextLabel.setText(state.getStateName());
            if (state instanceof BlackTurnState) {
                turnLabel.setBackground(Color.BLACK); // Oder Dunkelgrau
            } else {
                turnLabel.setBackground(Color.WHITE);
            }
        });
        return turnPanel;
    }
}