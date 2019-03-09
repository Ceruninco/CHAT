package com.company;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class Fenetre extends JFrame{
    int WIDTH = 500;
    int HEIGHT = 500;
    public Fenetre(){
        super("Carrion");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void paint(Graphics g){
        g.setColor(Color.green);
        g.fillRect(0,0, WIDTH, HEIGHT);

    }
    public static void main(){
        Fenetre fen = new Fenetre();
    }
    }

