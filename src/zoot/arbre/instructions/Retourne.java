package zoot.arbre.instructions;

import zoot.arbre.expressions.Expression;

public class Retourne extends Instruction {

    private Expression exp;

    public Retourne(Expression e,int n) {
        super(n);
        exp = e;
    }

    @Override
    public void verifier() {
        //TODO
    }

    @Override
    public String toMIPS() {
        String mips = "#Retourne\n";
        mips += "li $v0, " + exp.toMIPS() + "\n";
        mips += "jr $ra\n";
        return mips;
    }
}
