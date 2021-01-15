package common.src.main;

import java.util.ArrayList;

public class Bubble {
    public static final double GRAVITY = 0.05;
    private int size; 
    int borderHeight, borderWidth; 
    private String color;
    private double speedX = 1, speedY = 0; 
    private Point bubble;

    public Bubble(int size, String color, Point pos, int borderHeight, int borderWidth, int speedX) {
        this.size = size;
        this.color = color;
        this.speedX = speedX;
        this.bubble = pos;
        this.borderHeight = borderHeight;
        this.borderWidth = borderWidth;
    }

    public void move() {
        bubble.setX(bubble.getX() + speedX);
        bubble.setY(bubble.getY() + speedY);

        speedY = speedY + GRAVITY;

        if (bubble.getY() > borderHeight - this.size) {
            speedY = speedY * -0.99;
            bubble.setY(borderHeight - this.size);
        } 
        if (bubble.getX() > borderWidth - this.size || bubble.getX() < 0) {
            speedX = speedX * -1;
        }
    }

    public ArrayList<Bubble> addSplitBubbles() {
    	ArrayList<Bubble> newBubbles = new ArrayList<Bubble>();
        newBubbles.add(new Bubble(this.size/3*2, this.color, new Point(bubble.getX(),bubble.getY()), this.borderHeight, this.borderWidth, -1));
        newBubbles.add(new Bubble(this.size/3*2, this.color, new Point(bubble.getX(),bubble.getY()), this.borderHeight, this.borderWidth, 1)); 
        return newBubbles;
    }

    public boolean collisionWithArrow(Arrow arrow) {
        int radius = this.size/2;
        
        double testX = bubble.getX();
        double testY = bubble.getY();
        if (bubble.getX() < arrow.getX()) {
        	testX = arrow.getX();
        } else if (bubble.getX() > arrow.getX()+arrow.getArrowWidth()) {
        	testX = arrow.getX() + arrow.getArrowWidth();
        }
        
        if (bubble.getY() < arrow.getY()) {
        	testY = arrow.getY();
        } else if (bubble.getY() > arrow.getY()+arrow.getArrowHeight()) {
        	testY = arrow.getY() + arrow.getArrowHeight();
        }

        double distX = bubble.getX() - testX;
        double distY = bubble.getY() - testY;
        double distance = Math.sqrt((distX*distX) + (distY*distY));

        if (distance <= radius) {
            return true;
        } else {
            return false;
        }

    }

    public Point getPos() {
        return this.bubble;
    }

    public int getSize() {
    	return this.size;
    }
 }