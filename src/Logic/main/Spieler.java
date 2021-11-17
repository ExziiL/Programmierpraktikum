package Logic.main;

//import java.awt.*;

import Logic.Spielfeld.*;

public abstract class Spieler {

    protected Spielfeld spielfeld;
    protected String name;
    protected int anz_schuesse;
    protected int anz_treffer;
    protected int anz_zerstört;

    protected static int anz_zuege;

    public String schießen;

    public Spieler(Spielfeld spielfeld, String name) {
        this.spielfeld = spielfeld;
        this.name = name;
    }

    public abstract void schießen();

    public abstract void schiffSetzen();

    public void statistik() {

        System.out.println("Anzahl Schüsse: " + anz_schuesse);
        System.out.println("Anzahl Treffer: " + anz_treffer);
        System.out.println("Zerstört: " + anz_zerstört);
    }

    ;

    // public Point feuern(){}

    /*
     * public Point getroffen(){ switch (spielarray[""][""]){ case 0:
     * System.out.println("Verfehlt!");break; case 1:
     * System.out.println("Treffer!");break; case 2:
     * System.out.println("Versenkt!!!");break; default: break; } ; }
     */
}
