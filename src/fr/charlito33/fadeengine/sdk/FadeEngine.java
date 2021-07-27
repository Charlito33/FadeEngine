package fr.charlito33.fadeengine.sdk;

import fr.charlito33.fadeengine.sdk.math.Vector2i;

import javax.swing.*;

public class FadeEngine {
    private JFrame frame;
    JPanel panel; //Package Private

    public FadeEngine(Vector2i size) {
        frame = new JFrame();
        panel = new JPanel();

        //19

        frame.add(panel);

        frame.setSize(size.getX(), size.getY());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public int getWidth() {
        return panel.getWidth();
    }

    public int getHeight() {
        return panel.getHeight();
    }
}
