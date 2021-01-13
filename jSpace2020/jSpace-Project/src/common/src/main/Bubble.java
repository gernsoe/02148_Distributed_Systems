package common.src.main;

public class Bubble {
    public static final double GRAVITY = 0.01;
    private int bubbleID, size; 
    int borderHeight = 650, borderWidth = 650; 
    private String color;
    private int speedX = 1, speedY = 1, dirVertical = 1, dirHorizontal = 1;
    private Point bubble;

    public Bubble(int id, int size, String color, Point pos, int dirHorizontal, int dirVertical) {
        this.bubbleID = id;
        this.size = size;
        this.color = color;
        this.bubble = pos;
        this.dirHorizontal = dirHorizontal;
        this.dirVertical = dirVertical;
    }

    public void move() {
        moveHorizontal();
        moveVertical();
        //accelerate(0, -GRAVITY);
    }

    private void moveHorizontal() {
        // Calculate the next move, from the middle of the bubble
        double nextMove = bubble.getX() + dirHorizontal * (speedX + this.size/2);
        
        //Check for collision with walls
        if (nextMove < borderWidth && nextMove > 0) {
            double newX = bubble.getX() + dirHorizontal * speedX;
            bubble.setX(newX);
        } else if (nextMove >= borderWidth) {
            changeDirHorizontal();
            bubble.setX(borderWidth+dirHorizontal*this.size/2); // Move bubble back onto the map
        } else if (nextMove <= 0) {
            changeDirHorizontal();
            bubble.setX(0+dirHorizontal*this.size/2);// Move bubble back onto the map
        }
        System.out.println("X: " + bubble.getX());
    }

    private void moveVertical() {
        // Calculate the next move, from the middle of the bubble
        double nextMove = bubble.getY() + dirVertical * (speedY + this.size/2);

        //Check for collision with walls
        if (nextMove < borderHeight && nextMove > 0) {
            double newY = bubble.getY() + dirVertical * speedY;
            bubble.setY(newY);
        } else if (nextMove >= borderHeight) {
            changeDirVertical();
            bubble.setY(borderHeight+dirVertical*this.size/2); // Move bubble back onto the map
        } else if (nextMove <= 0) {
            changeDirVertical();
            bubble.setY(0+dirVertical*this.size/2); // Move bubble back onto the map
        }
        System.out.println("Y: " + bubble.getY());
    }

    public Bubble[] kill(int id) {
        Bubble[] bubbles = new Bubble[2];
        Bubble left = new Bubble(id + 1, this.size/2, this.color, this.bubble, -1, 0);
        Bubble right = new Bubble(id + 2, this.size/2, this.color, this.bubble, 1, 0);
        bubbles[0] = left;
        bubbles[1] = right;        
        return bubbles;
    }

    private void accelerate(double accelerationX, double accelerationY) {
        speedX += accelerationX;
        speedY += accelerationY;
    }

    private void changeDirHorizontal() {
        dirHorizontal *= -1;
    }

    private void changeDirVertical() {
        dirVertical *= -1;
    }

    public Point getPos() {
        return this.bubble;
    }
    
    public int getID() {
        return this.bubbleID;
    }

}