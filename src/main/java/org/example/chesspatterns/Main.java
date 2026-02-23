package org.example.chesspatterns;

import org.example.chesspatterns.model.board.Board;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Patterns");

        String iconName = "P-B.png";
        try {
            frame.setIconImage(javax.imageio.ImageIO.read
                    (Objects.requireNonNull
                            (ClassLoader.getSystemResourceAsStream(iconName))));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load icon", e);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setLayout(new GridBagLayout());
        Icon undoIcon;
        Icon resetIcon;
        try {
            undoIcon = new ImageIcon(javax.imageio.ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("undo.png"))).getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            resetIcon = new ImageIcon(javax.imageio.ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("restart.png"))).getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        JButton resetButton = new JButton(resetIcon);
        JButton undoButton = new JButton(undoIcon);

        Board board = new Board();

        undoButton.addActionListener(e -> board.undoMove());
        resetButton.addActionListener(e -> board.resetBoard());

        controlPanel.add(resetButton);
        controlPanel.add(undoButton);

        frame.add(controlPanel);
        frame.add(board);

        frame.pack();
        // Put the window on the 3rd screen (index 2), centered; fallback to current behavior.
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        int targetScreenIndex = 1; // 0-based: 0=1st, 1=2nd, 2=3rd
        if (screens.length > targetScreenIndex) {
            Rectangle bounds = screens[targetScreenIndex].getDefaultConfiguration().getBounds();
            int x = bounds.x + (bounds.width - frame.getWidth()) / 2;
            int y = bounds.y + (bounds.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);
        } else {
            frame.setLocationRelativeTo(null);
        }
        frame.setVisible(true);


    }
}