package Logic;

import Logic.Ausnahmen.FalscheSpielfeldGroesse;

public class GegnerSpielfeld extends Spielfeld {

    public GegnerSpielfeld(int groesse) throws FalscheSpielfeldGroesse {
        super(groesse);

    }
}
