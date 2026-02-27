package org.example.chesspatterns.view;

import org.example.chesspatterns.core.PromotionHandler;
import org.example.chesspatterns.pattern.factory.PieceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SwingPromotionHandler implements PromotionHandler {

    private final Component parent;

    public SwingPromotionHandler(Component parent) {

        this.parent = parent;
    }

    @Override
    public PieceType handlePromotion(boolean isWhite) {
        // 1. Einen modalen Dialog erstellen (blockiert das Spiel, bis gewählt wurde)
        JDialog dialog = new JDialog((Frame) parent, "Promotion", true);
        dialog.setUndecorated(true); // Optional: Entfernt die Fensterleiste für cleaneren Look
        dialog.setLayout(new GridLayout(1, 4, 10, 10)); // 4 Buttons nebeneinander mit Abstand

        // Ergebnis-Container (da wir aus dem ActionListener nicht direkt returnen können)
        final PieceType[] result = {PieceType.QUEEN}; // Standard-Wert

        // 2. Die 4 Auswahl-Buttons erstellen
        JButton btnQueen = createButton(isWhite ? "Q-W.png" : "Q-B.png", PieceType.QUEEN, result, dialog);
        JButton btnRook = createButton(isWhite ? "R-W.png" : "R-B.png", PieceType.ROOK, result, dialog);
        JButton btnBishop = createButton(isWhite ? "B-W.png" : "B-B.png", PieceType.BISHOP, result, dialog);
        JButton btnKnight = createButton(isWhite ? "N-W.png" : "N-B.png", PieceType.KNIGHT, result, dialog);

        // 3. Buttons zum Dialog hinzufügen
        dialog.add(btnQueen);
        dialog.add(btnRook);
        dialog.add(btnBishop);
        dialog.add(btnKnight);

        // 4. Dialog Größe und Position
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(parent); // Zentriert auf dem Bildschirm

        // Optional: Dicken Rahmen, damit es sich vom Brett abhebt
        ((JPanel) dialog.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        dialog.setVisible(true); // WARTET HIER, bis dialog.dispose() aufgerufen wird

        return result[0];
    }

    private JButton createButton(String iconName, PieceType type, PieceType[] resultHolder, JDialog dialog) {
        JButton button = new JButton();

        // Bild laden und skalieren
        ImageIcon icon = loadIcon(iconName);
        if (icon != null) {
            button.setIcon(icon);
        } else {
            button.setText(type.toString()); // Fallback Text
        }

        // Styling für "flache" Spiel-Buttons
        button.setFocusPainted(false);
        button.setBackground(Color.LIGHT_GRAY);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Klick-Logik
        button.addActionListener(e -> {
            resultHolder[0] = type;
            dialog.dispose(); // Schließt den Dialog und beendet waitForUserSelection()
        });

        return button;
    }

    private ImageIcon loadIcon(String resourceName) {
        try (InputStream is = getClass().getResourceAsStream("/" + resourceName)) {
            if (is == null) return null;
            BufferedImage img = ImageIO.read(is);
            // Skalieren auf z.B. 70x70 Pixel für gute Sichtbarkeit
            Image scaled = img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


