package zoot.arbre.expressions;

import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Negatif extends Expression{

    private Expression exp;

    public Negatif(Expression e, int n) {
        super(n);
        exp = e;
    }

    @Override
    public void verifier() {
        exp.verifier();
        try {
            if(!exp.getType().equals("entier")){
                throw new TypeIncompatibleException(exp, noLigne);
            }
        } catch (TypeIncompatibleException erreur) {
            CollectExcept.getInstance().addException(erreur);
        }
    }

    @Override
    public String toMIPS() {
        String mips = exp.toMIPS();
        mips += "#Empile pour negatif\nsw $v0,($sp) \nadd $sp,$sp,-4\n";
        mips += "#Dépile \nadd $sp,$sp,4\nlw $t8,($sp)\n";
        mips += "li $v0, -1\n";
        mips += "mul $v0, $t8, $v0\nmflo $v0\n";
        return mips;
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String getNom() {
        return "-" + exp.getNom();
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
