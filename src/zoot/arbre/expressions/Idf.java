package zoot.arbre.expressions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;
import zoot.arbre.declarations.Entree;

public class Idf extends Expression{
    private Entree e;
    private Symbole s;

    public Idf(Entree entree, int n) {
        super(n);
        e = entree; //nom de la variable
    }

    @Override
    public void verifier() {
        s = TDS.getInstance().identifier(e); //vérification de l'existence
    }

    @Override
    public String toMIPS() {
        return s.getDecalage()+"($s7)";
    }

    public String getType(){
        return this.s.getType();
    }
}
