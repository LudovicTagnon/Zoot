package zoot.arbre.expressions.binaire;

import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Et extends Binaire{
    private String label = "et" + getNoLigne();
    private String operator;
    public Et(Expression eg, Expression ed, int n, boolean and) {
        super(eg, ed, n);
        if (and) {
            operator = "and";
        } else {
            operator = "or";
        }
    }

    @Override
    public String getNom() {
        return expGauche.getNom() + operator + expDroite.getNom();
    }

    @Override
    public void verifier() {
        expGauche.verifier();
        expDroite.verifier();
        try{
            if (!expGauche.getType().equals("booleen") || !expDroite.getType().equals("booleen")) {
                throw new TypeIncompatibleException(expGauche, expDroite, noLigne);
            }
        }catch (TypeIncompatibleException e){
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String mips = expGauche.toMIPS();
        mips += "#Empile \nsw $v0,($sp) \nadd $sp,$sp,-4\n";


        if (!expDroite.isFonc()) {
            if (expDroite.isConstante()) { //si exp est une constante
                if (expDroite.getType().equals("booleen")) { //si exp est un booléen
                    mips += "la "; //load
                } else { //si exp est un entier
                    mips += "li "; //load
                }

            } else {
                mips += "lw "; //load word si exp est une variable
            }

            mips += "$v0, " + expDroite.toMIPS(); //store la valeur dans v0
        } else { //si exp est une fonction
            mips += expDroite.toMIPS();
        }

        mips += "#Dépile \nadd $sp,$sp,4 \nlw $t8,($sp) \n";
        mips += operator+" $v0, $t8, $v0 \n";
        mips += "move $t0, $v0\n";
        mips += "beq $s0, $v0, Sinon" + label + "\n";
        mips += "la $v0, vrai\n";
        mips += "b FinSi" + label + "\n";
        mips += "Sinon" + label + ":" + "\n";
        mips += "la $v0, faux\n";
        mips += "FinSi" + label + ":" + "\n";
        return mips;
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public boolean isConstante() {
        return expGauche.isConstante();
    }

    @Override
    public boolean isFonc() {
        return expGauche.isFonc();
    }

    @Override
    public boolean isOperator() {
        return true;
    }
}
