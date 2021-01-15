package common.src.main;

public class Bubble {
    public static final double GRAVITY = 0.05;
    private int bubbleID, size; 
    int borderHeight, borderWidth; 
    private String color;
    private double speedX = 1, speedY = 0; 
    private Point bubble;

    public Bubble(int id, int size, String color, Point pos, int borderHeight, int borderWidth) {
        this.bubbleID = id;
        this.size = size;
        this.color = color;
        this.bubble = pos;
        this.borderHeight = borderHeight;
        this.borderWidth = borderWidth;
    }

    public void move() {
        bubble.setX(bubble.getX() + speedX);
        bubble.setY(bubble.getY() + speedY);

        speedY = speedY + GRAVITY;

        if (bubble.getY() > borderHeight - this.size) {
            speedY = speedY * -0.96;
            bubble.setY(borderHeight - this.size);
        } 
        if (bubble.getX() > borderWidth - this.size || bubble.getX() < 0) {
            speedX = speedX * -1;
        }
    }

    public Bubble[] kill(int id) {
        Bubble[] bubbles = new Bubble[2];
        Bubble left = new Bubble(id + 1, this.size/2, this.color, this.bubble, this.borderHeight, this.borderWidth);
        Bubble right = new Bubble(id + 2, this.size/2, this.color, this.bubble, this.borderHeight, this.borderWidth);
        bubbles[0] = left;
        bubbles[1] = right;        
        return bubbles;
    }

    public boolean collisionWithArrow(Arrow arrow) {
        int radius = this.size/2;

        double distX = bubble.getX() - arrow.getX();
        double distY = bubble.getY() - arrow.getY();
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
    
    public int getID() {
        return this.bubbleID;
    }
    
    public int getSize() {
    	return this.size;
    }
 }