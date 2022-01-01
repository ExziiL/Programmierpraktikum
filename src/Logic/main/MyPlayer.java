package Logic.main;

import Logic.Game.Game;

public class MyPlayer extends Player {


    private String name;

    public MyPlayer(Game game, String name) {
        super(game);
        this.name = name;
    }

    @Override
    public void takeTurn() {

    }
}