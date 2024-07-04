package com.cargame.src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Obstacle {
    private int x;
    int y;
    private final int width = 50, height = 90;
    private BufferedImage obstacleImage;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("/com/cargame/images/obstacle.png"));
            obstacleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = obstacleImage.createGraphics();
            g.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
        } catch (IOException e) {
            throw new RuntimeException("Obstacle image not found", e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(obstacleImage, x, y, null);
    }

    public void move() {
        y += 1;
        if (y > 600) {
            y = 0;
            x = (int) Math.random();
        }
        if (x < 50) {
            x = 50;
        }
        if (x > 350 - width) {
            x = 350 - width;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isPassed() {
        throw new UnsupportedOperationException("Unimplemented method 'isPassed'");
    }

    public void setPassed(boolean b) {
        throw new UnsupportedOperationException("Unimplemented method 'setPassed'");
    }

    public int getY() {
        throw new UnsupportedOperationException("Unimplemented method 'getY'");
    }
}