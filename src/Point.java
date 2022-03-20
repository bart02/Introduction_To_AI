import java.util.Random;

public class Point extends java.awt.Point {
    public Point(java.awt.Point p) {
        super(p);
    }

    public Point(int x, int y) {
        super(x, y);
    }

    public static Point generate() {
        Random random = new Random();
        return new Point(random.nextInt(8), random.nextInt(8));
    }

    public void translate(Point p) {
        translate(p.x, p.y);
    }

    public Point translateNew(Point p) {
        Point n = new Point(this);
        n.translate(p);
        return n;
    }

    public Point translateNew(int dx, int dy) {
        Point n = new Point(this);
        n.translate(dx, dy);
        return n;
    }

    public int manhattanDistance(Point p) {
        return Math.abs(p.x - this.x) + Math.abs(p.y - this.y);
    }
}