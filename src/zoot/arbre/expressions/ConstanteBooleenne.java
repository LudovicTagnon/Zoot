package zoot.arbre.expressions;

public class ConstanteBooleenne extends Constante{

    private boolean valeur;

    public ConstanteBooleenne(String val, int n) {
        super(val, n);
        valeur = Boolean.parseBoolean(val);
    }
    @Override
    public String toMIPS() {
        if(valeur){
            return "li $v0, 1\n";
        }else{
            return "li $v0, 0\n";
        }
    }

    @Override
    public String getType() {
        return "booleen";
    }
}
