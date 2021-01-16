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

    public Bubble(int size, Color color, Point pos, int borderHeight, int borderWidth, int speedX) {
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
/*
public boolean collisionWithArrow(Arrow arrow) {
    int radius = this.size/2;
	/*boolean inside1 = checkInsideBubble((int)arrow.getX(),borderHeight, radius);
	boolean inside2 = checkInsideBubble((int)arrow.getX()+1,(int)arrow.getY(), radius);
	if (inside1 || inside2) return true;
	
	double distX = arrow.getX() - (arrow.getX()+1);
	double distY = borderHeight - arrow.getY();
    double len = Math.sqrt((distX*distX) + (distY*distY));
   
    double dot = ((bubble.getX()-arrow.getX())+((bubble.getY()-borderHeight)*(arrow.getY()-borderHeight)))/(len*len);
    
    double closestX = arrow.getX() + dot;
    double closestY = borderHeight + (dot * (arrow.getY()-borderHeight));
    
    boolean onArrow = checkOnArrow(arrow.getX(),borderHeight,arrow.getX()+1,arrow.getY(),closestX,closestY);
    if (!onArrow) return false;
    //		
    double testX = bubble.getX();
    double testY = bubble.getY();
    //double testY = bubble.getY();
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
    
    double distX = (bubble.getX())-testX;
    double distY = bubble.getY()-testY;
    double distance =  Math.sqrt((distX*distX) + (distY*distY));

    distX = closestX - bubble.getX();
    distY = closestY - bubble.getY();
    double distance = Math.sqrt((distX*distX) + (distY*distY));
	
    if (distance <= radius) {
    	System.out.println("ArrowHeight" + arrow.getArrowHeight());
    	System.out.println("DistY" + distY);
    	System.out.println("DistX" + distX);
    	System.out.println("Distance" + distance);
    	System.out.println("Radius " + radius);
    	System.out.println("Arrow" + arrow.getArrowPos().toString());
    	System.out.println("Bubble" + bubble.toString());
        return true;
    } else {
        return false;
    }

}

private boolean checkOnArrow(double x1, int y1, double x2, double y2, double closestX, double closestY) {
	
	double d1 = Math.sqrt((y1-closestY)*(y1-closestY)+(x1-closestX)*(x1-closestX));
	double d2 = Math.sqrt((y2-closestY)*(y2-closestY)+(x2-closestX)*(x2-closestX));
	double len = Math.sqrt((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
	double buffer = 0.1;
	
	if (d1+d2 >= len-buffer && d1+d2 <= len+buffer) {
		return true;
	}
	return false;
}

public boolean checkInsideBubble(int x, int y, int radius) {
	double distX = x - bubble.getX();
	double distY = y - bubble.getY();
	double dist = Math.sqrt((distX*distX)+(distY*distY));
	
	if (dist <= radius) {
		return true;
	} else {
		return false;
	}
}*/	