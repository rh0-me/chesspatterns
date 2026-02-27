package org.example.chesspatterns.pattern.flyweight;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static ImageCache instance;
    private final Map<String, ImageIcon> cache = new HashMap<>();

    private ImageCache() {
    }

    public static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public ImageIcon getImage(String imageName) {
        // Prüfen, ob das Bild schon im Cache (Flyweight) liegt
        if (!cache.containsKey(imageName)) {
            // Wenn nicht: Lade es aus dem "resources" Ordner
            // Der Pfad /assets/ geht davon aus, dass deine Bilder in einem Ordner namens "assets" im Classpath liegen
            URL imgUrl = getClass().getResource("/" + imageName + ".png");

            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);

                // Bild skalieren, damit es gut in das JPanel passt (z.B. 60x60 Pixel)
                Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                cache.put(imageName, new ImageIcon(scaledImage));

            } else {
                System.err.println("Bild nicht gefunden: /assets/" + imageName + ".png");
                return null; // Fallback, falls das Bild fehlt
            }
        }
        return cache.get(imageName); // Gibt das gecachte Bild zurück
    }
    
    public ImageIcon getScaledImage(String imageName, int width, int height) {
        ImageIcon originalIcon = getImage(imageName);
        if (originalIcon != null) {
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null; 
    }

    public Image getIcon() {
        return getImage("K-B").getImage();
    }
}
