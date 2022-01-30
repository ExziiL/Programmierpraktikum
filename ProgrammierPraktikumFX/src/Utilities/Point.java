package Utilities;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the Coordinates of two Points are equal
     *
     * @param obj a Point
     * @return true if Points are equal
     */

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
