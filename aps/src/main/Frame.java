package main;

import javax.swing.*;

public class Frame extends JFrame {

    private final GamePanel gp;

    public Frame(){
        this.setTitle("Patrulha verde");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        gp = new GamePanel();
        this.add(gp);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        gp.startGameThread();
    }


}
