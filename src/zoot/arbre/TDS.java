package zoot.arbre;

import zoot.arbre.declarations.Entree;
import zoot.arbre.declarations.EntreeFonction;
import zoot.exceptions.DoubleDeclarationException;
import zoot.exceptions.NonDeclarerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TDS {

    private static TDS instance = new TDS();
    private int actuel; // Bloc actuel
    private ArrayList<HashMap<Entree, Symbole>> bloc ; // Tableau de bloc

    private TDS() {
        HashMap <Entree, Symbole> table = new HashMap<>(3);
        bloc = new ArrayList<>();
        bloc.add(table); // On ajoute le bloc 0
        actuel = 0; // On commence au bloc 0
    }

    public static TDS getInstance() {
        return instance ;
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclarationException {
        HashMap<Entree, Symbole> table = bloc.get(actuel);
        //Si la variable existe déjà, on lève une exception
        for (Entree entree : table.keySet()) {
            if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrée
                throw new DoubleDeclarationException(e);
            }
        }

        s.setDecalage(getTailleVariable()); //décalage négatif pour les variables
        bloc.get(s.getNumBloc()).put(e, s); // On met l'élément dans la table de hashage
    }

    public Symbole identifier(Entree e)  throws NonDeclarerException {

        HashMap<Entree, Symbole> table= bloc.get(0);
        Symbole s = null;

        for (Entree entree : table.keySet()) {
            if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrée
                s = table.get(entree);
            }
        }

        table = bloc.get(actuel);
        for (Entree entree : table.keySet()) {
            if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrées
                s = table.get(entree);
            }
        }

        if (s == null) {
            throw new NonDeclarerException(e);
        }

        return s;
    }

    public Symbole identifier(EntreeFonction e, int nbPar) throws NonDeclarerException { //surcharge de la méthode identifier pour les fonctions
        HashMap<Entree, Symbole> table= bloc.get(0);
        Symbole s = null;

        for (Map.Entry<Entree, Symbole> row : table.entrySet()) {
            Entree entree = row.getKey();
            Symbole symbole = row.getValue();
            if (entree.getNom().equals(e.getNom()) && symbole.getNbParams() == nbPar) { //compare les noms des Entrées et le nbr de paramètres
                s = table.get(entree);
            }
        }

        if (s == null) {
            throw new NonDeclarerException(e);
        }

        return s;
    }

    public int getTailleVariable() {
        int cpt = 0;
        for (HashMap<Entree, Symbole> table : bloc) {
            cpt += table.size(); // On compte le nombre de variable dans chaque bloc
        }
        return cpt*-4;
    }

    public void  entreebloc() {
        if (actuel == 0) {
            HashMap<Entree, Symbole> table = new HashMap<>(5);
            bloc.add(table);
        }
        actuel = bloc.size()-1; // On se place sur le dernier bloc
    }

    public void sortieBloc() {
        actuel = 0; // On se replace sur le bloc 0
    }

    public int getBlocActuel() {
        return actuel;
    }

    public void setBlocActuel(int i) {
        actuel = i;
    }

    public int getNbrPar() { // On compte le nombre de paramètres du bloc actuel
        int cpt = 0;
        HashMap<Entree, Symbole> table = bloc.get(actuel);
        for (Symbole s : table.values()) {
            if (s.isParametre()) { // On vérifie si c'est un paramètre
                cpt++;
            }
        }
        return cpt;
    }

    public String toString() {
        String s = "";
        for (HashMap<Entree, Symbole> table : bloc) {
            for (Map.Entry<Entree, Symbole> row : table.entrySet()) {
                Entree entree = row.getKey();
                Symbole symbole = row.getValue();
                s += entree.getNom() + " " + symbole.getDecalage() + " " + symbole.getType() + " " + symbole.getNumBloc() + "\n";
            }
        }
        return s;
    }
}
