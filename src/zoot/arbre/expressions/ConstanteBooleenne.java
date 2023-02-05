package zoot.arbre.expressions;

public class ConstanteBooleenne extends Constante{

    private boolean valeur;

    public ConstanteBooleenne(String val, int n) {
        super(val, n);
        if (val.equals("vrai")) {
            valeur = true;
        } else {
            valeur = false;
        }
    }
    @Override
    public String toMIPS() {
        if(valeur){
            return "1\n";
        }else{
            return "0\n";
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
