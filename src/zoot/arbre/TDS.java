package zoot.arbre;

import zoot.arbre.declarations.Entree;
import zoot.exceptions.DoubleDeclarationException;
import zoot.exceptions.NonDeclarerException;

import java.util.ArrayList;
import java.util.HashMap;

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

        //Si la variable n'existe pas, on lève une exception
        for (Entree entree : table.keySet()) {
            if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrée
                s = table.get(entree);
            }
        }

        table = bloc.get(actuel);
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
}
