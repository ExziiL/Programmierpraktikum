package Logic.Game;

import Logic.Game.Exceptions.FalscherSpielertyp;

import static Logic.main.LogicConstants.*;
public class MyGame extends Game {

    public MyGame(){
        super();
    }

    @Override
    public void setName(String n) {
        super.setName(n);
        try {
          this.player = createPlayer(n, PlayerType.SELF);
        }catch (FalscherSpielertyp e){
            System.out.println("Falscher Spielertyp");
        }
    }
}
