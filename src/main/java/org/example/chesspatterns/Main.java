package org.example.chesspatterns;

import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.pattern.flyweight.ImageCache;
import org.example.chesspatterns.pattern.memento.BoardMemento;
import org.example.chesspatterns.view.BoardView;
import org.example.chesspatterns.view.SwingPromotionHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

            frame.add(boardView);
            frame.setVisible(true);
        });
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