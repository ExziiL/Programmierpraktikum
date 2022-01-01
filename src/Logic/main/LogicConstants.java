package Logic.main;

public class LogicConstants {

    public static enum PlayerType {
        ONLINE, OFFLINE, SELF
    }

    public static enum GameElementStatus {
        WATER, SHIP, HIT, CLOSE, ERROR, OVERLAP,MISS
    }

    public static enum GameMode {
        OFFLINE, ONLINE
    }
}
