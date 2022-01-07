package Utilities;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Point p = (Point) obj;
            if (this.x == p.x && this.y == p.y) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
