package zoot.arbre.declarations;
import java.util.ArrayList;
import java.util.Iterator;

public class LFCT {

    private ArrayList<Fonction> fonctions;
    private boolean debut;
    private static LFCT instance = new LFCT();

    private LFCT() {
        fonctions = new ArrayList<>();
        debut = true;
    }

    public static LFCT getInstance() {
        return instance;
    }

    public void addFonction(Fonction f) {
        fonctions.add(f);
    }

    public void setDebut(boolean debut) {
        this.debut = debut;
    }

    public boolean getDebut() {
        return debut;
    }

    public Iterator<Fonction> getIterator() {
        return fonctions.iterator();
    }
}
