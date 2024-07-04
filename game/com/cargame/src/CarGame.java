package com.cargame.src;

import javax.swing.JFrame;

public class CarGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Game");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}