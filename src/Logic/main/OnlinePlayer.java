package Logic.main;

import Logic.Game.Game;

public class OnlinePlayer extends Player {

    public OnlinePlayer(Game game) {
        super(game);
    }

    @Override
    public boolean takeTurn() {

        //TODO Warten auf Antwort des Gegners
        return false;
    }

}
