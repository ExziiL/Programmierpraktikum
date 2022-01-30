package Logic.Game;


import static Logic.main.LogicConstants.GameMode;
import static Logic.main.LogicConstants.PlayerType;

public class MyGame extends Game {
    /**
     * Constructor for a MyGame-Object
     */
    public MyGame() {
        super();

    }

    @Override
    public void setGameMode(GameMode m) {
        super.setGameMode(m);
        switch (gameMode) {
            case ONLINE:
                player = createPlayer(PlayerType.ONLINE);
                break;
            case OFFLINE:
                player = createPlayer(PlayerType.OFFLINE);
                break;
        }
    }

    public int enemyTurn() {
        return player.takeTurn();
    }
}
