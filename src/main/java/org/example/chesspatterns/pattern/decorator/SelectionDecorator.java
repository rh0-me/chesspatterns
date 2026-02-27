package org.example.chesspatterns.pattern.decorator;

import org.example.chesspatterns.view.SquareView;

import javax.swing.*;
import java.awt.*;

public class SelectionDecorator extends SquareDecorator {
    public SelectionDecorator(SquareView decoratedSquare) {
        super(decoratedSquare);
    }

    @Override
    public JPanel getPanel() {
        JPanel panel = super.getPanel();
        // Fügt einen dicken blauen Rahmen als Markierung hinzu
        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
        return panel;
    }
}
