package common.src.main;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.*;

public class Bubble {
    public static final double GRAVITY = 0.05;
    private int size; 
    int borderHeight, borderWidth; 
    private Color color;
    private double speedX = 1, speedY = 0; 
    private Point bubble;

    public Bubble(int size, Color color, Point pos, int borderHeight, int borderWidth, int speedX, int speedY) {
        this.size = size;
        this.color = color;
        this.speedX = speedX;
        this.speedY = speedY;
        this.bubble = pos;
        this.borderHeight = borderHeight;
        this.borderWidth = borderWidth;
    }

    public void move() {
        bubble.setX(bubble.getX() + speedX);
        bubble.setY(bubble.getY() + speedY);

        speedY = speedY + GRAVITY;

        // Bounce of bottom
        if (bubble.getY() > borderHeight - this.size) {
            speedY = speedY * -0.99;
            bubble.setY(borderHeight - this.size);
        } 
        // Bounce of top
        if (bubble.getY() < 0) {
            speedY = speedY * -0.99;
            bubble.setY(0.1);
        }
        // Bounce of right wall
        if (bubble.getX() > borderWidth - this.size) {
            speedX = speedX * -1;
            bubble.setX(borderWidth - this.size);
        }
        // Bounce of left wall
        if (bubble.getX() < 0) {
            speedX = speedX * -1;
            bubble.setX(0.1);
        }

    }

    public ArrayList<Bubble> addSplitBubbles() {
    	ArrayList<Bubble> newBubbles = new ArrayList<Bubble>();
        newBubbles.add(new Bubble(this.size/3*2, this.color, new Point(bubble.getX(),bubble.getY()), this.borderHeight, this.borderWidth, -1, -3));
        newBubbles.add(new Bubble(this.size/3*2, this.color, new Point(bubble.getX(),bubble.getY()), this.borderHeight, this.borderWidth, 1, -3)); 
        return newBubbles;
    }

	public Ellipse2D getShape(){
		return new Ellipse2D.Double(bubble.getX(),bubble.getY(),this.size,this.size);
	}
	
    public Point getPos() {
        return this.bubble;
    }

    public int getSize() {
    	return this.size;
    }
    
    public Color getColor() {
    	return color;
    }
 }
