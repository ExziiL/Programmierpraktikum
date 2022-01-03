package Logic.main;

public class LogicConstants {

    public enum PlayerType {
        ONLINE, OFFLINE, SELF
    }

    public enum GameElementStatus {
        WATER, SHIP, HIT, CLOSE, ERROR, OVERLAP, MISS
    }

    public enum GameMode {
        OFFLINE, ONLINE
    }

    public enum Direction {
        RIGHT, LEFT, UP, DOWN, NONE
    }
}
