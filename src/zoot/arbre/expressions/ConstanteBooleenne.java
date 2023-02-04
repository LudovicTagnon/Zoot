package zoot.arbre.expressions;

public class ConstanteBooleenne extends Constante{

    private boolean valeur;

    public ConstanteBooleenne(String val, int n) {
        super(val, n);
        valeur = Boolean.parseBoolean(val);
    }

}
