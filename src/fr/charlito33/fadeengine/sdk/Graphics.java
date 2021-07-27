package fr.charlito33.fadeengine.sdk;

import fr.charlito33.fadeengine.sdk.math.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Graphics {
    private final FadeEngine engine;
    private final Graphics2D graphics;

    private final Font defaultFont;

    public Graphics(FadeEngine engine) {
        this.engine = engine;
        graphics = (Graphics2D)engine.panel.getGraphics();
        defaultFont = graphics.getFont();
    }

    public void draw() {
        graphics.clearRect(0, 0, engine.panel.getWidth(), engine.panel.getHeight());
    }

    public void drawImage(BufferedImage image, Vector2i pos) {
        graphics.drawImage(image, pos.getX(), pos.getY(), null);
    }

    public void setFont(Font font) {
        graphics.setFont(font);
    }

    public void drawText(String text, Vector2i pos) {
        graphics.drawString(text, pos.getX(), pos.getY());
    }

    public void drawTextOutline(String text, Vector2i pos, int size) {
        graphics.setStroke(new BasicStroke(size));
        graphics.translate(pos.getX(), pos.getY());
        GlyphVector gv = graphics.getFont().createGlyphVector(graphics.getFontRenderContext(), text);
        int length = gv.getNumGlyphs();
        graphics.draw(gv.getOutline());
        resetPos();
    }

    public void setColor(Color color) {
        graphics.setColor(color);
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void resetPos() {
        graphics.translate(-graphics.getTransform().getTranslateX(), -graphics.getTransform().getTranslateY());
    }

    public void reset() {
        resetPos();
        graphics.setColor(Color.BLACK);
        graphics.setFont(defaultFont);
        graphics.setStroke(new BasicStroke(1));
    }
}
