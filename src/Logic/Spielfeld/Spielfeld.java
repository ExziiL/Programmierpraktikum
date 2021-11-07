package Logic.Spielfeld;

import Logic.Spielfeld.Ausnahmen.*;

import static Logic.main.LogicKonstanten.*;

import Logic.main.*;


public class Spielfeld {
    private int groesse;
    private int[][] spielfeld;

    public Spielfeld(int groesse) throws FalscheSpielfeldGroesse {

        if (groesse < 5 || groesse > 30) {
            throw new FalscheSpielfeldGroesse(groesse);
        }

        this.groesse = groesse;
        spielfeld = new int[groesse][groesse];
    }

    public void initialisiereSpielfeld() {
        System.out.println("Spielfeld initialisiert");
    }

    private Spieler erstelleSpieler(String name, Spielertyp t) throws FalscherSpielertyp {
        Spieler neuerSpieler;

        // erstelle je nach Spielertyp eine Unterklasse des Typ Spieler

        if (t == t.OFFLINE) {
            neuerSpieler = new OfflineSpieler(this, name);

        } else if (t == t.ONLINE) {

            neuerSpieler = new OnlineSpieler(this, name);
        } else {
            throw new FalscherSpielertyp();
        }
        return neuerSpieler;
    }


}
