package org.example.chesspatterns.view;

import org.example.chesspatterns.controller.BoardController;
import org.example.chesspatterns.core.GameManager;
import org.example.chesspatterns.model.board.Board;
import org.example.chesspatterns.model.pieces.Piece;
import org.example.chesspatterns.pattern.decorator.SelectionDecorator;
import org.example.chesspatterns.pattern.decorator.ValidMoveDecorator;
import org.example.chesspatterns.pattern.flyweight.ImageCache;
import org.example.chesspatterns.pattern.observer.BoardObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardView extends JPanel implements BoardObserver {
    private final Board board;
    private final SquareView[][] squares = new SquareView[8][8];
    private BoardController controller;

    public BoardView(Board board) {
        this.board = board;
        this.board.addObserver(this);
        setLayout(new GridLayout(8, 8));

        this.controller = new BoardController(this, GameManager.getInstance());

        initializeBoardUI();

        updateUIFromModel();
    }

    private void initializeBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color color = ((row + col) % 2 == 0) ? new Color(240, 217, 181) : new Color(181, 136, 99);

                SquareView square = new BaseSquare(color);

                JLabel pieceLabel = new JLabel("", SwingConstants.CENTER);
                pieceLabel.setFont(new Font("Serif", Font.PLAIN, 40));
                square.getPanel().add(pieceLabel);


                // MOUSE LISTENER hinzufügen (Leitet Klick an Controller weiter)
                final int r = row;
                final int c = col;
                square.getPanel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        controller.handleSquareClick(r, c);
                    }
                });


                squares[row][col] = square;
                add(square.getPanel());
            }
        }
    }


    public void highlightSquare(int row, int col) {
        clearHighlights(); // Alte Markierungen löschen

        // Das Feld mit dem Decorator einwickeln
        squares[row][col] = new SelectionDecorator(squares[row][col]);

        // Swing zwingen, das Update zu zeichnen
        squares[row][col].getPanel().repaint();
    }

    public void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].resetVisuals();

                // Falls es dekoriert war, wieder auf das reine BaseSquare zurücksetzen
                // (In einer echten App würde man die Basis-Referenz sauberer verwalten, 
                // aber für das visuelle Reset reicht resetVisuals() hier völlig aus).
            }
        }
    }

    @Override
    public void onBoardChanged() {
        System.out.println("GUI aktualisiert sich!");
        updateUIFromModel();
        repaint();
    }

    private void updateUIFromModel() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = board.getPiece(row, col);
                // Das JLabel ist die erste Componente (Index 0) in unserem Square-Panel
                JLabel label = (JLabel) squares[row][col].getPanel().getComponent(0);

                if (p != null) {
                    // 1. Text leeren (falls da noch Unicode drin stand)
                    label.setText("");

                    // 2. Bild aus dem Flyweight-Cache holen
                    ImageIcon icon = ImageCache.getInstance().getImage(p.getImageName());

                    // 3. Bild auf das Label setzen
                    label.setIcon(icon);
                } else {
                    // Feld ist leer -> Icon entfernen
                    label.setIcon(null);
                    label.setText("");
                }
            }
        }
    }

    public void highlightValidMoves(List<int[]> validMoves) {
        for (int[] move : validMoves) {
            int row = move[0];
            int col = move[1];

            // Das Zielfeld mit dem ValidMoveDecorator umwickeln
            squares[row][col] = new ValidMoveDecorator(squares[row][col]);
            squares[row][col].getPanel().repaint();
        }
    }
}
