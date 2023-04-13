package zoot.arbre.expressions.binaire;

import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Inf extends Binaire{

    private String label = "inf" + getNoLigne(); //TODO: peut poser problème si plusieurs inf sur la même ligne
    public Inf(Expression eg, Expression ed, int n) {
        super(eg, ed, n);
    }

    @Override
    public String getNom() {
        return expGauche.getNom() + " < " + expDroite.getNom();
    }

    @Override
    public void verifier() {
        expGauche.verifier();
        expDroite.verifier();
        try{
            if (!expGauche.getType().equals("entier") || !expDroite.getType().equals("entier")) {
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

        mips += "#Dépile \nadd $sp,$sp,4\nlw $t8,($sp)\n";
        mips += "#Branch if less than\n";
        mips += "ble $t8,$v0,si_"+label+"\n";
        mips += "li $v0, 0\n";
        mips += "j end_"+label+"\n";
        mips += "si_" +label+":\n";
        mips += "li $v0, 1\n";
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
