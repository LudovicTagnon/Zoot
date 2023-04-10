package zoot.arbre.expressions.binaire;

import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.TypeIncompatibleException;

public class Inf extends Binaire{

    String label = "inf" + getNoLigne();
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
                System.out.println("Erreur de type : impossible d'effectuer une addition entre " + expGauche.getType() + " et " + expDroite.getType() + " à la ligne " + noLigne + ".");
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
        mips += "ble $v0,$t8,si_"+label+"\n";
        mips += "li $v0, 0\n";
        mips += "j end_"+label+"\n";
        mips += "si_" +label+":\n";
        mips += "li $v0, 1\n";
        mips += "end_"+label+":\n";
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
}
