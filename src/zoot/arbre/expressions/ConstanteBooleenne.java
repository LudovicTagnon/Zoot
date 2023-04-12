package zoot.arbre.expressions;

public class ConstanteBooleenne extends Constante{

    public ConstanteBooleenne(String val, int n) {
        super(val, n);
    }

    @Override
    public String toMIPS() {
        if(cste.equals("vrai")){
            return "1\n";
        } else if (cste.equals("faux")){
            return "0\n";
        } else { //N'est jamais atteint normalement (supprimé ?)
            throw new IllegalArgumentException("La constante booleenne doit être vrai ou faux");
        }
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public boolean isConstante() {
        return true;
    }
}
