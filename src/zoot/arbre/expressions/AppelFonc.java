package zoot.arbre.expressions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;
import zoot.arbre.declarations.Entree;

public class AppelFonc extends Expression{

    //Similaire à Idf
    Entree e;
    Symbole s;

    public AppelFonc(Entree entree, int n) {
        super(n);
        e = entree;
    }

    @Override
    public void verifier() {
        s = TDS.getInstance().identifier(e);
    }

    @Override
    public String toMIPS() {
        //TODO: Voir pour register des registre etc...
        String mips = "\n#Saut vers une fonction";
        mips += "\njal "+e.getNom()+"\n";
        return mips;
    }

    @Override
    public String getType() {
        return s.getType();
    }

    @Override
    public boolean isFonc() {
        return true;
    }

    @Override
    public String getNom() {
        return e.getNom();
    }
}
