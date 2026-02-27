package org.example.chesspatterns;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.memento.BoardMemento;
import org.example.chesspatterns.view.BoardPanel;
import org.example.chesspatterns.view.SwingPromotionHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Patterns");

        try {
            var iconStream = ClassLoader.getSystemResourceAsStream("P-B.png");
            if (iconStream != null) {
                frame.setIconImage(ImageIO.read(iconStream));
            }
        } catch (IOException e) {
            System.err.println("Failed to load icon: " + e.getMessage());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Board boardModel = new Board();
        boardModel.setStartingPosition();

        GameManager.getInstance().initializeGame(boardModel);
        GameManager.getInstance().setPromotionHandler(new SwingPromotionHandler(frame));

        GameManager.getInstance().askPromotionChoice(true);

        BoardPanel boardView = new BoardPanel(boardModel);
        boardModel.addObserver(boardView);

        frame.setLayout(new BorderLayout());

        Icon undoIcon;
        Icon resetIcon;
        try {
            var undoStream = ClassLoader.getSystemResourceAsStream("undo.png");
            var resetStream = ClassLoader.getSystemResourceAsStream("restart.png");
            if (undoStream == null) {
                System.err.println("undo.png not found in resources");
            }
            if (resetStream == null) {
                System.err.println("restart.png not found in resources");
            }

            undoIcon = new ImageIcon(ImageIO.read(undoStream)
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            resetIcon = new ImageIcon(ImageIO.read(resetStream)
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            undoIcon = null;
            resetIcon = null;

            System.err.println("Failed to load control icons: " + e.getMessage());
        }

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        JButton resetButton = resetIcon != null ? new JButton(resetIcon) : new JButton("Reset");
        JButton undoButton = undoIcon != null ? new JButton(undoIcon) : new JButton("Undo");

        JButton quickSaveButton = new JButton("Quick Save");
        quickSaveButton.addActionListener(_ -> {
            GameManager.getInstance().quickSaveSlot = boardModel.createMemento();
            JOptionPane.showMessageDialog(frame, "Game quick saved");
        });

        JButton quickLoad = new JButton("Quick Load");
        quickLoad.addActionListener(_ -> {
            if (GameManager.getInstance().quickSaveSlot != null) {
                boardModel.restoreFromMemento(GameManager.getInstance().quickSaveSlot);
            } else {
                JOptionPane.showMessageDialog(frame, "No quick save available");
            }
        });

        undoButton.addActionListener(_ -> {
            boardModel.undoMove();
            boardView.repaint();
        });
        resetButton.addActionListener(_ -> {
            boardModel.resetBoard();
            boardView.repaint();
        });

        controlPanel.add(resetButton);
        controlPanel.add(undoButton);
        controlPanel.add(quickSaveButton);
        controlPanel.add(quickLoad);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(boardView, BorderLayout.CENTER);

        frame.pack();

        positionFrameOnScreen(frame, 1);
        frame.setVisible(true);
    }

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
}