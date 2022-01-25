package Logic.main;

public class Ship {
    protected int size;
    protected boolean isHorizontal;
    private int hash;

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public Ship(int s) {
        this.size = s;
    }

    public Ship(int s, boolean horizontal) {
        this.size = s;
        this.isHorizontal = horizontal;
    }

    public int getSize() {
        return size;
    }

   public int getHash() {
       return hash;
   }

    public void setHash(int hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return " " + size + " " + isHorizontal + " " + this.hashCode();
    }


}
