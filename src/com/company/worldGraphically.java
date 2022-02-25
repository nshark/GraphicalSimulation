package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Objects;

import static java.lang.Math.abs;

public class worldGraphically extends Frame implements KeyListener {
    public JPanel panel;
    public Canvas canvas;
    public float c = 3;
    public float a = 1;
    public float b = 2;
    public int width = 1000;
    public int height = 750;
    worldGraphically(){
        new JFrame("world");
        this.setUpGraphics();
        new Thread(this::run).start();
    }
    public void setUpGraphics(){
        panel = new JPanel();
        canvas = new Canvas();
        canvas.setBounds(0,0, width, height);
        add(panel);
        panel.setLayout(null);
        panel.add(canvas);
        setPreferredSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBounds(0,0,width,height);
        pack();
        canvas.requestFocus();
        canvas.setIgnoreRepaint(true);
        canvas.createBufferStrategy(2);
        canvas.addKeyListener(this);
        setVisible(true);
        setResizable(false);
    }
    @SuppressWarnings("LoopConditionNotUpdatedInsideLoop")
    public void run(){
        boolean done = false;
        while(!done){
            this.render();
        }
    }
    private float roundTo(float p){
        float i = Math.round(p*10);
        return i/10;
    }
    public void render(){
        Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics().create();
        g.clearRect(0,0,width,height);
        g.setColor(Color.black);
        drawQLine(g, a, b, c);
        g.drawLine(0,375,1000,375);
        g.drawLine(500,0,500,750);
        g.drawString(("a: " + roundTo(a)  + "\nb: " + roundTo(b) + "\nc: " + roundTo(c)), 10,10);
        g.drawString(("y = " + roundTo(a) + "*x^2 + " + roundTo(b) + "*x + " + roundTo(c)), 10, 30);
        g.drawString(("Min: " + findMinQLine(a, b, c)), 10, 50);
        canvas.getBufferStrategy().show();
        g.dispose();
    }
    public void drawQLine(Graphics2D g, float a, float b, float c){
        for (float i = -50; i < 50; i+=0.1) {
            for (float j = -50; j < 50; j+=0.1) {
                if((int)(a*(i*i) + b*i + c) == (int)j){
                    if (abs(roundTo(i)) < 0.5 || abs(roundTo(j)) < 0.5){
                        g.setColor(Color.red);
                    }
                    else{
                        g.setColor(Color.black);
                    }
                    g.fillRect((int) ((i*10)-500)*-1, (int) ((j*10)-375)*-1, 5, 5);
                }
            }
        }
    }
    public double findMinQLine(float a, float b, float c){
        double search = 10;
        double place = 10;
        double small = 1000000000;
        for (int i = 0; i < 500; i++) {
            double t1 = a * Math.pow((place+search), 2) + b * (place+search) + c;
            double t2 = a * Math.pow((place-search), 2) + b * (place-search) + c;
            if (t1 > small && t2 > small) {
                search = search/2;
            }
            else if(t1 > t2){
                place = place - search;
                small = t2;
            }
            else if(t1 == t2){
                return(small);
            }
            else{
                place = place + search;
                small = t1;
            }
        }
        if(abs(small) < 0.001){
            return(0);
        }
        return(small);
    }
    @Override
    public void keyTyped(KeyEvent e) {
        if(Objects.equals(e.getKeyChar(), 'a')){
            a -= 0.1;
        }
        if(Objects.equals(e.getKeyChar(), 'd')){
            a+= 0.1;
        }
        if(Objects.equals(e.getKeyChar(), 's')){
            b -= 0.1;
        }
        if(Objects.equals(e.getKeyChar(), 'w')){
            b+= 0.1;
        }
        if(Objects.equals(e.getKeyChar(), 'q')){
            c -= 0.1;
        }
        if(Objects.equals(e.getKeyChar(), 'e')){
            c+= 0.1;
        }
        if(Objects.equals(e.getKeyChar(), 'A')){
            a -= 1;
        }
        if(Objects.equals(e.getKeyChar(), 'D')){
            a+= 1;
        }
        if(Objects.equals(e.getKeyChar(), 'S')){
            b -= 1;
        }
        if(Objects.equals(e.getKeyChar(), 'W')){
            b+= 1;
        }
        if(Objects.equals(e.getKeyChar(), 'Q')){
            c -= 1;
        }
        if(Objects.equals(e.getKeyChar(), 'E')){
            c+= 1;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}