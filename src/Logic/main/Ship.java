package Logic.main;

public class Ship {
    protected int size;
    protected boolean isHorizontal;

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public Ship(int s) {
        this.size = s;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Ship{" + "groesse=" + size +'}';
    }
}
