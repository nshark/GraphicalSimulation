package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Objects;

import static java.lang.Math.*;

public class worldGraphically extends Frame implements KeyListener, MouseListener, MouseMotionListener {
    public JPanel panel;
    public Canvas canvas;
    public int pmx = 10000;
    public int pmy = 10000;
    public float c = 3;
    public double scale = 10;
    public float a = 1;
    public float b = 2;
    private float Mx = 0;
    private float My = 0;
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
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
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
    private float roundTo(float p, int n){
        float i = Math.round(p*(pow(10, n)));
        return (float) (i/Math.pow(10, n));
    }
    public void render(){
        Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics().create();
        g.clearRect(0,0,width,height);
        g.setColor(Color.black);
        drawQLine(g, a, b, c);
        if(!(scale*My + 375 < 0 || scale*My + 375 > 750)) {
            g.drawLine(0, (int) (375 + (scale * My)), 1000, (int) (375 + (scale * My)));
        }
        if (!(scale*Mx+500 < 0 || scale*Mx + 500 > 1000)) {
            g.drawLine((int) (500 + (scale * Mx)), 0, (int) (500 + (scale * Mx)), 750);
        }
        g.drawString(("a: " + roundTo(a,1)  + "\nb: " + roundTo(b,1) + "\nc: " + roundTo(c,1)), 10,10);
        g.drawString(("y = " + roundTo(a,1) + "*x^2 + " + roundTo(b,1) + "*x + " + roundTo(c,1)), 10, 30);
        if(findMinQLine(a, b, c) == -1000000){
            g.drawString(("Min: Negative Infinity"), 10, 50);
        }
        else {
            g.drawString(("Min: " + findMinQLine(a, b, c)), 10, 50);
        }
        canvas.getBufferStrategy().show();
        g.dispose();
    }
    public void drawQLine(Graphics2D g, float a, float b, float c){
        for (float i = (float) (-500/scale); i < 500/scale; i+=1/scale) {
            for (float j = (float) (-500/scale); j < 500/scale; j+=1/scale) {
                if((roundTo(a*((i+Mx)*(i+Mx)) + b*(i+Mx) + c, (int) log10(scale)) == roundTo((j+My), (int) log10(scale)))){
                    g.fillRect((int) ((i*scale)-500)*-1, (int) ((j*scale)-375)*-1, (int) (40/scale)+1, (int) (40/scale)+1);
                }
            }
        }
    }
    public double findMinQLine(float a, float b, float c){
        if(a == 0 || a < 0){
            return(-1000000);
        }
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
        if (Objects.equals(e.getKeyChar(), 'z')){
            scale+= scale/10;
        }
        if (Objects.equals(e.getKeyChar(), 'x')){
            scale-= scale/10;
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pmx = 10000;
        pmy = 10000;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pmx != 10000 || pmy != 10000) {
            Mx -= (pmx - e.getX())/scale;
            My -= (pmy - e.getY())/scale;
        }
        pmx = e.getX();
        pmy = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}