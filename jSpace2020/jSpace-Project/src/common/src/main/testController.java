package common.src.main;

public class testController {
    public static void main(String[] args) {
        Bubble bubble = new Bubble(0, 10, "green", new Point(10,650), -1, 0);

        while (true) {
            bubble.move();
            Bubble[] bubbles = bubble.kill(bubble.getID());
            bubble = null;
        }
    }
}
