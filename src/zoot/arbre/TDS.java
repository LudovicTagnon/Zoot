package zoot.arbre;

import zoot.arbre.declarations.Entree;
import zoot.arbre.declarations.Fonction;
import zoot.exceptions.DoubleDeclarationException;
import zoot.exceptions.NonDeclarerException;

import java.util.ArrayList;
import java.util.HashMap;

public class TDS {

    private static TDS instance = new TDS();
    private HashMap<Entree, Symbole> table ;

    //TODO: cherhcer a relocaliser ça (autre singleton ?)
    private ArrayList<Fonction> fonctions; //provisoire
    private boolean debut = true; //provisoire

    private TDS() {
        table = new HashMap<>(3);
        fonctions = new ArrayList<>();
    }

    public static TDS getInstance() {
        return instance ;
    }

    public void ajouterFonction(Fonction f) {
        fonctions.add(f);
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclarationException {
        //Si la variable existe déjà, on lève une exception
        for (Entree entree : table.keySet()) {
            if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrée
                throw new DoubleDeclarationException(e);
            }
        }

        s.setDecalage(table.size()*-4); //décalage négatif pour les variables
        table.put(e, s); // On met l'élément dans la table de hashage
    }

    public Symbole identifier(Entree e)  throws NonDeclarerException {
        //Si la variable n'existe pas, on lève une exception
        Symbole s = null;
        for (Entree entree : table.keySet()) {
            if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrée
                s = table.get(entree);
            }
        }

        if (s == null) {
            throw new NonDeclarerException(e);
        }

        return s;
    }

    public int getTailleVariable() {
        return table.size()*-4;
    }

    public ArrayList<Fonction> getFonctions() {
        return fonctions;
    }

    public void setDebut(boolean debut) {
        this.debut = debut;
    }

    public boolean getDebut() {
        return debut;
    }

    public void  entréebloc() {
        table = new HashMap<>();
    }

    public void sortieBloc() {
        table.clear();
    }
}
