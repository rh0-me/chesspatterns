package org.example.chesspatterns;

import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.view.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Patterns");

        try {
            var iconStream = ClassLoader.getSystemResourceAsStream("P-B.png");
            if (iconStream != null) {
                frame.setIconImage(javax.imageio.ImageIO.read(iconStream));
            }
        } catch (IOException e) {
            System.err.println("Failed to load icon: " + e.getMessage());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Board boardModel = new Board();

        BoardPanel boardView = new BoardPanel(boardModel);

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

            undoIcon = new ImageIcon(javax.imageio.ImageIO.read(undoStream)
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            resetIcon = new ImageIcon(javax.imageio.ImageIO.read(resetStream)
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            undoIcon = null;
            resetIcon = null;

            System.err.println("Failed to load control icons: " + e.getMessage());
        }

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        JButton resetButton = resetIcon != null ? new JButton(resetIcon) : new JButton("Reset");
        JButton undoButton = undoIcon != null ? new JButton(undoIcon) : new JButton("Undo");


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