package Logic.main;

public class Controller {
    public Controller() {
    }
    private int spielfeldgroesse;

    public void SpielLaden() {

    }

    public void SpielStarten() {

    }

    public void SpielSpeichern() {
    }

    public void Einstellungen() {
    }

    public void beenden() {
        System.exit(0);
    }

    public void setName(String s) {
        System.out.println(s);
    }

    public void setSpielfeldGroesse(int n) {
        spielfeldgroesse = n;
        System.out.println(n);
    }
    public  int getSpielfeldgroesse(){
        return spielfeldgroesse;
    }

}
