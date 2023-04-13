package zoot.arbre.expressions;

import zoot.arbre.TDS;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Not extends Expression{

    Expression exp;
    String label = "not" + TDS.getInstance().getCpt();

    public Not(Expression e, int n) {
        super(n);
        exp = e;
    }

    @Override
    public void verifier() {

        exp.verifier();
        try {
            if (!exp.getResultType().equals("booleen")) {
                throw new TypeIncompatibleException(exp, noLigne);
            }
        } catch (TypeIncompatibleException e) {
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String mips = exp.toMIPS();
        mips += "#Branch if equal\n";
        mips += "beq $v0,$s0,si_"+label+"\n"; //s0 = faux
        mips += "li $v0, 0\n"; //si vrai alors faux
        mips += "j end_"+label+"\n";
        mips += "si_" +label+":\n";
        mips += "li $v0, 1\n"; //si faux alors on mets à vrai
        mips += "end_"+label+":\n";
        return mips;
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public String getNom() {
        return "non " + exp.getNom();
    }

    @Override
    public boolean isConstante() {
        return exp.isConstante();
    }

    @Override
    public boolean isFonc() {
        return exp.isFonc();
    }

    @Override
    public boolean isOperator() {
        return true;
    }
}
