package main;

import Ausnahmen.FalscheSpielfeldGroesse;

public class EigenesSpielfeld extends Spielfeld {

    public EigenesSpielfeld(int groesse) throws Ausnahmen.FalscheSpielfeldGroesse {
        super(groesse);
    }
}
