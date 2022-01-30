package Logic.main;

public class Ship {

    //region Variables
    protected int size;
    protected boolean isHorizontal;
    private int hash;
    //endregion

    /**
     * Constructor for an Object of Ship
     * @param s Ship-Size
     */
    public Ship(int s) {
        this.size = s;
    }

    /**
     * Constructor for an Object of Ship
     * @param s Ship-Size
     * @param horizontal is the Ship Rotation horizontal(true) or vertical(false)
     */
    public Ship(int s, boolean horizontal) {
        this.size = s;
        this.isHorizontal = horizontal;
    }

    /**
     *
     * @return the Rotation of the Ship (true - horizontal, false - vertical)
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Sets the isHorizontal Variable
     * @param horizontal Rotation of the Ship
     */
    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
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
