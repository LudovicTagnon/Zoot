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

        s.setDecalage(table.size()*-4); //décalage négatif pour les variables
        table.put(e, s); // On met l'élément dans la table de hashage
    }

    public Symbole identifier(Entree e)  throws NonDeclarerException {

        HashMap<Entree, Symbole> table;
        Symbole s = null;
        for (int i = 0; i<=actuel; i++) { // On parcourt les blocs (priorité au bloc actuel)
            table = bloc.get(i);
            //Si la variable n'existe pas, on lève une exception
            for (Entree entree : table.keySet()) {
                if (entree.getNom().equals(e.getNom())) { //compare les noms des Entrée
                    s = table.get(entree);
                }
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
        actuel++;
        if (actuel == 0) {
            HashMap<Entree, Symbole> table = new HashMap<>(3);
            bloc.add(table);
        }
    }

    public void sortieBloc() {
        bloc.remove(actuel);
        actuel--;
    }
}
