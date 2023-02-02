package zoot.arbre;

import zoot.arbre.declarations.Entree;
import zoot.exceptions.DoubleDeclarationException;
import zoot.exceptions.NonDeclarerException;

import java.util.HashMap;

public class TDS {

    private static TDS instance = new TDS();
    private HashMap<Entree, Symbole> table ;

    private TDS() {
        table = new HashMap<>(3);
    }

    public static TDS getInstance() {
        return instance ;
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclarationException {
        //Si la variable existe déjà, on lève une exception
        if (table.containsKey(e)){
            throw new DoubleDeclarationException(e);
        }

        s.setDecalage(table.size()*-4); //décalage négatif pour les variables
        table.put(e, s); // On met l'élément dans la table de hashage
    }

    public Symbole identifier(String idf)  throws NonDeclarerException {
        //Si la variable n'existe pas, on lève une exception
        if (!table.containsKey(idf)){
            throw new NonDeclarerException(idf);
        }
        return table.get(idf);
    }

}
