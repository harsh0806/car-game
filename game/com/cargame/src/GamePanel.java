package com.cargame.src;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Car car;
    private ArrayList<Obstacle> obstacles;
    private boolean gameStarted;
    private boolean gameOver;
    private long startTime;
    private int obstacleWidth = 50;
    private int obstacleHeight = 90;
    private BufferedImage backgroundImage;

    public GamePanel() {
        setFocusable(true);
        setPreferredSize(new Dimension(400, 600));
        car = new Car(175, 475);
        obstacles = new ArrayList<>();
        gameStarted = false;
        gameOver = false;
        loadBackgroundImage();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameStarted && !gameOver) {
                    startGame();
                }
            }
        });
        addKeyListener(this);
        timer = new Timer(16, this);
    }

    private void loadBackgroundImage() {
        try {
            BufferedImage originalImage1 = ImageIO.read(getClass().getResource("/com/cargame/images/bg.png"));
            backgroundImage = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = backgroundImage.createGraphics();
            g.drawImage(originalImage1.getScaledInstance(400, 600, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
        } catch (IOException e) {
            throw new RuntimeException("Background image not found", e);
        }
    }

    private void startGame() {
        gameStarted = true;
        timer.start();
        startTime = System.currentTimeMillis();
        Timer obstacleTimer = new Timer(4000, e -> {
            spawnObstacle();
        });
        obstacleTimer.start();
    }

    private void spawnObstacle() {
        Random rand = new Random();
        int x = rand.nextInt(getWidth() - obstacleWidth);
        int y = -obstacleHeight;
        Rectangle newObstacleBounds = new Rectangle(x, y, obstacleWidth, obstacleHeight);
        boolean overlap = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(newObstacleBounds)) {
                overlap = true;
                break;
            }
        }
        if (!overlap) {
            obstacles.add(new Obstacle(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (!gameStarted && !gameOver) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 26));
            FontMetrics fm = g2d.getFontMetrics();
            String message = "Click to Start";
            int x = (getWidth() - fm.stringWidth(message)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(message, x, y);
        } 
        else {
            car.draw(g2d);
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(g2d);
            }
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            long score = (System.currentTimeMillis() - startTime) / 1000;
            g2d.drawString("Score: " + score, 10, 40);
            if (gameOver) {
                // Display "Game Over" message
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                FontMetrics fm = g2d.getFontMetrics();
                String gameOverMessage = "Game Over!";
                int x = (getWidth() - fm.stringWidth(gameOverMessage)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(gameOverMessage, x, y);
                g2d.setFont(new Font("Arial", Font.BOLD, 25));
                FontMetrics fm1 = g2d.getFontMetrics();
                String gameScoreMessage = "Your Score is " + score;
                int x1 = (getWidth() - fm1.stringWidth(gameScoreMessage)) / 2;
                int y1 = getHeight() - 30;
                g2d.drawString(gameScoreMessage, x1, y1);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            car.move();
            for (Obstacle obstacle : obstacles) {
                obstacle.move();
            }
            checkCollisions();
            repaint();
        }
    }

    private void checkCollisions() {
        Rectangle carBounds = car.getBounds();
        for (Obstacle obstacle : obstacles) {
            if (carBounds.intersects(obstacle.getBounds())) {
                timer.stop();
                gameOver = true;
                System.out.println("Game Over!");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameStarted && !gameOver) {
            car.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameStarted && !gameOver) {
            car.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}