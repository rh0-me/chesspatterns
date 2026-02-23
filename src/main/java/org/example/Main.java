package org.example;

import javax.swing.*;
import javax.swing.border.Border;
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
            frame.setIconImage(javax.imageio.ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(iconName))));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load icon", e);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setLayout(new GridBagLayout());
        frame.add(new Board());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}