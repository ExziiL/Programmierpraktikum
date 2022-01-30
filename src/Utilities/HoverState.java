package Utilities;

import Logic.main.LogicConstants.GameElementStatus;

public final class HoverState {

    private int index = 0;
    private GameElementStatus status = GameElementStatus.WATER;
    private int part = 0;
    private int shipSize = 0;
    private boolean isHorizontal = false;

    public HoverState(int index, GameElementStatus status){
        this.index = index;
        this.status = status;
    }

    public HoverState(int index, GameElementStatus status, int part, int shipSize, boolean isHorizontal) {
        this(index, status);
        this.part = part;
        this.shipSize = shipSize;
        this.isHorizontal = isHorizontal;
    }

    public int getShipSize() {
        return shipSize;
    }

    public void setShipSize(int shipSize) {
        this.shipSize = shipSize;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getIndex() {
        return index;
    }

    public GameElementStatus getStatus() {
        return status;
    }
}
