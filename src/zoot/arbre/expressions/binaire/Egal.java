package zoot.arbre.expressions.binaire;

import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Egal extends Binaire{

    private String label = "egal" + getNoLigne();
    private boolean isEqual;
    public Egal(Expression eg, Expression ed, int n, boolean equal) {
        super(eg, ed, n);
        isEqual = equal;
    }

    @Override
    public String getNom() {
        String operateur = "==";
        if (!isEqual) {
            operateur = " != ";
        }
        return expGauche.getNom() + operateur + expDroite.getNom();
    }

    @Override
    public void verifier() {
        expGauche.verifier();
        expDroite.verifier();
        try {
            if (!expGauche.getType().equals(expDroite.getType())) {
                throw new TypeIncompatibleException(expGauche, expDroite, noLigne);
            }
        } catch (TypeIncompatibleException e) {
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String si;
        String sinon;

        if (isEqual) {
            si = "faux";
            sinon = "vrai";
        } else {
            si = "vrai";
            sinon = "faux";
        }

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

        mips += "#Dépile \nadd $sp,$sp,4\nlw $t8,($sp)\n";
        mips += "#Branch if equal\n";
        mips += "beq $v0,$t8,si_"+label+"\n";
        mips += "la $v0, "+si+"\n";
        mips += "j end_"+label+"\n";
        mips += "si_" +label+":\n";
        mips += "la $v0, "+sinon+"\n";
        mips += "end_"+label+":\n";
        return mips;
    }

    @Override
    public String getType() {
        return expGauche.getType();
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

    @Override
    public String getResultType() {
        return "booleen";
    }
}
