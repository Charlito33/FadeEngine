package fr.charlito33.fadeengine.sdk;

import fr.charlito33.fadeengine.sdk.math.Vector2i;
import fr.charlito33.fadeengine.sdk.utils.ImagesUtils;
import fr.charlito33.fadeengine.sdk.utils.RepeatedTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameLogic {
    private float start = 0, diff, wait;
    private int fps;

    private FadeEngine engine;
    private Graphics graphics;

    public GameLogic(int fps) {
        this.fps = fps;
    }

    public void setGraphics(FadeEngine engine, Graphics graphics) {
        this.engine = engine;
        this.graphics = graphics;
    }

    public void start() {
        BufferedImage image = null;
        Font font = null;

        try {
            image = ImageIO.read(ResourcesManager.getResource("engine/assets/images/background.png"));
            font = Font.createFont(Font.TRUETYPE_FONT, ResourcesManager.getResource("engine/assets/fonts/relate.ttf"));
        } catch (IOException e) {
            System.err.println("Can't get Fade Background !");
            e.printStackTrace();
        } catch (FontFormatException e) {
            System.err.println("Can't get Fade Font !");
            e.printStackTrace();
        }

        String text = "Made with Fade";

        graphics.drawImage(ImagesUtils.resize(image, engine.getWidth(), engine.getHeight()), Vector2i.get(0, 0));

        graphics.setFont(font.deriveFont(100f));
        graphics.setColor(Color.WHITE);

        int posX = (engine.getWidth() / 2) - (graphics.getGraphics().getFontMetrics().stringWidth(text) / 2);
        int posY = (engine.getHeight() / 2);
        Vector2i pos = new Vector2i(posX, posY);

        graphics.setColor(Color.BLACK);
        graphics.drawTextOutline(text, pos, 6);
        graphics.setColor(Color.WHITE);
        graphics.drawText(text, pos);
        graphics.reset();

        //TODO: Remove this
        /*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("Can't sleep Thread !");
            e.printStackTrace();
        }

         */

        new RepeatedTask(() -> {
            graphics.draw();
        }, 60);

    }

    private void capFrameRate() {
        try {
            wait = 1f / fps;
            diff = Math.round(System.nanoTime() / 1000f) - start;
            if (diff < wait) {
                Thread.sleep((long)(wait - diff) * 1000L);
            }
            start = Math.round(System.nanoTime() / 1000f);
        } catch (InterruptedException e) {
            System.err.println("Can't Cap Framerate !");
            e.printStackTrace();
        }
    }

}
