package Utilities.Hover;

import Logic.main.LogicConstants.GameElementStatus;

public final class HoverState {

    private int index = 0;
    private GameElementStatus status = GameElementStatus.WATER;

    public HoverState(int index, GameElementStatus status) {
        this.index = index;
        this.status = status;
    }

    public int getIndex() {
        return index;
    }

    public GameElementStatus getStatus() {
        return status;
    }
}
