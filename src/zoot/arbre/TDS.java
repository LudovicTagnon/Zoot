package zoot.arbre;

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

    public void ajouter(String idf, Symbole s) { //throws Exception a faire
        //if (table.containsKey(idf))
        s.setDecalage(table.size()*-4); //décalage négatif pour les variables
        table.put(idf, s);
    }

    public Symbole existe(String idf) { //throws Exception a faire plus tard
        //if (!table.containsKey(idf))
        return table.get(idf);
    }

}
