package zoot.arbre.declarations;
import zoot.arbre.Symbole;
import zoot.arbre.expressions.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LFCT {

    private ArrayList<Fonction> fonctions;
    private boolean debut;
    private static LFCT instance = new LFCT();

    private HashMap<Entree, Symbole> params;
    private ArrayList<Expression> par_apl; //Paramètres d'une fonction lors de son appel

    private LFCT() {
        fonctions = new ArrayList<>();
        debut = true;
        params = new HashMap<>();
        par_apl = new ArrayList<>();
    }

    public void addFonction(Fonction f) {
        fonctions.add(f);
    }

    public void stockerParams(Entree entree , Symbole sym) { //Stocke provisoirement les paramètres d'une fonction
        params.put(entree, sym);
    }

    public void stockerParamsApl(Expression exp) { //Stocke provisoirement les paramètres d'une fonction
        par_apl.add(exp);
    }

    public HashMap<Entree, Symbole> destockParams() { //Renvoie les paramètres d'une fonction
        HashMap<Entree, Symbole> p = new HashMap<>(params); //On crée une copie profonde de parametres
        params.clear(); //On vide la liste des paramètres pour la prochaine fonction
        return p;
    }

    public ArrayList<Expression> destockParamsApl() { //Renvoie les paramètres d'un appel de fonction
        ArrayList<Expression> p = new ArrayList<>(par_apl); //On crée une copie profonde de parametres
        par_apl.clear(); //On vide la liste des paramètres pour la prochaine fonction
        return p;
    }

    //Getters et Setters

    public static LFCT getInstance() {
        return instance;
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
