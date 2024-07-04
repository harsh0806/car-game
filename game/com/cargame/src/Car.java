package com.cargame.src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Car {
    private int x;
    int y;
    private int dx, dy;
    private final int width = 50, height = 90;
    private BufferedImage carImage;

    public Car(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("/com/cargame/images/car.png"));
            carImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = carImage.createGraphics();
            g.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
        } catch (IOException e) {
            throw new RuntimeException("Car image not found", e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(carImage, x, y, null);
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 50) {
            x = 50;
        }
        if (x > 350 - width) {
            x = 350 - width;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > 575 - height) {
            y = 575 - height;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -6;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 6;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -6;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 6;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
