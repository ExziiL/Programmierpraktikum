package Logic.main;

public class Ship {
    protected int size;

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
