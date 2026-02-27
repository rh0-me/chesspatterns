package org.example.chesspatterns.view;


import javax.swing.*;
import java.awt.*;

public class BaseSquare implements SquareView {
    private final JPanel panel;
    private final Color originalColor;

    public BaseSquare(Color color) {
        this.originalColor = color;
        this.panel = new JPanel(new BorderLayout());
        this.panel.setBackground(color);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void resetVisuals() {
        panel.setBackground(originalColor);
        panel.setBorder(null);
    }

    @Override
    public SquareView unwrap() {
        return this;
    }
}
