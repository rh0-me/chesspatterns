package org.example.chesspatterns.pattern.decorator;

import org.example.chesspatterns.view.SquareView;

import javax.swing.*;

public abstract class SquareDecorator implements SquareView {
    protected final SquareView decoratedSquare;

    public SquareDecorator(SquareView decoratedSquare) {
        this.decoratedSquare = decoratedSquare;
    }

    @Override
    public JPanel getPanel() {
        return decoratedSquare.getPanel();
    }

    @Override
    public void resetVisuals() {
        decoratedSquare.resetVisuals();
    }

    @Override
    public SquareView unwrap() {
        return decoratedSquare.unwrap();
    }
}
