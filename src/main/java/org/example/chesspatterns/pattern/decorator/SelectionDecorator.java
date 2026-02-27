package org.example.chesspatterns.pattern.decorator;

import org.example.chesspatterns.view.SquareView;

import javax.swing.*;
import java.awt.*;

public class SelectionDecorator extends SquareDecorator {
    public SelectionDecorator(SquareView decoratedSquare) {
        super(decoratedSquare);
        super.getPanel().setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
    }
}
