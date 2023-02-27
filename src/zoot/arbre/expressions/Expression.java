package zoot.arbre.expressions;

import zoot.arbre.ArbreAbstrait;

public abstract class Expression extends ArbreAbstrait {
    
    protected Expression(int n) {
        super(n) ;
    }

    public abstract String getType();

    public boolean isConstante(){
        return false;
    }

    public boolean isFonc(){
        return false;
    }

}
