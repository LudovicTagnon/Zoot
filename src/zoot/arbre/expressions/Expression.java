package zoot.arbre.expressions;

import zoot.arbre.ArbreAbstrait;

public abstract class Expression extends ArbreAbstrait {
    
    protected Expression(int n) {
        super(n) ;
    }

    public abstract String getType();

    public String getResultType(){
        return getType();
    }

    public boolean isConstante(){
        return false;
    }

    public boolean isFonc(){
        return false;
    }

    public boolean isOperator(){
        return false;
    }

    public abstract String getNom();

}
