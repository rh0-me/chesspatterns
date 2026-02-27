package org.example.chesspatterns.pattern.decorator;

import org.example.chesspatterns.view.SquareView;

import javax.swing.*;
import java.awt.*;

public class ValidMoveDecorator extends SquareDecorator {
    public ValidMoveDecorator(SquareView decoratedSquare) {
        super(decoratedSquare);

        super.getPanel()
                .setBackground(new Color(144, 238, 144));
        super.getPanel()
                .setBorder(BorderFactory.createLineBorder(new Color(50, 205, 50), 4));
    }
}
