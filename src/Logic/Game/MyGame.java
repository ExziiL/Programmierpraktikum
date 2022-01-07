package Logic.Game;


import static Logic.main.LogicConstants.*;

public class MyGame extends Game {


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

    public boolean enemyTurn() {
        return player.takeTurn();
    }
}
