package zoot.arbre;

import zoot.exceptions.DoubleDeclarationException;
import zoot.exceptions.NonDeclarerException;

import java.util.HashMap;

public class TDS {

    private static TDS instance = new TDS();
    private HashMap<String, Symbole> table ;

    private TDS() {
        table = new HashMap<>(3);
    }

    public static TDS getInstance() {
        return instance ;
    }

    public void ajouter(String idf, Symbole s) throws DoubleDeclarationException {
        //Si la variable existe déjà, on lève une exception
        if (table.containsKey(idf)){
            throw new DoubleDeclarationException(idf);
        }

        s.setDecalage(table.size()*-4); //décalage négatif pour les variables
        table.put(idf, s);
    }

    public Symbole existe(String idf)  throws NonDeclarerException {
        //Si la variable n'existe pas, on lève une exception
        if (!table.containsKey(idf)){
            throw new NonDeclarerException(idf);
        }
        return table.get(idf);
    }

}
