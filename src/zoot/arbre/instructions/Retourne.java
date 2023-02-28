package zoot.arbre.instructions;

import zoot.arbre.TDS;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.ReturnException;

public class Retourne extends Instruction {

    private Expression exp;

    public Retourne(Expression e,int n) {
        super(n);
        exp = e;
    }

    @Override
    public void verifier() {
        exp.verifier();

        if (TDS.getInstance().getDebut()){
            throw new ReturnException(noLigne, "Retourne hors d'une fonction");
        }
    }

    @Override
    public String toMIPS() {
        String mips = "#Retourne\n";
        mips += "li $v0, " + exp.toMIPS() + "\n";
        mips += "jr $ra\n";
        return mips;
    }

    @Override
    public boolean isReturn() {
        return true;
    }
}
