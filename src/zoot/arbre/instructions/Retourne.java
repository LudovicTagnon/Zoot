package zoot.arbre.instructions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;
import zoot.arbre.declarations.EntreeFonction;
import zoot.arbre.declarations.Fonction;
import zoot.arbre.declarations.LFCT;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.ReturnException;

import java.util.Iterator;

public class Retourne extends Instruction {

    private Expression exp;

    public Retourne(Expression e,int n) {
        super(n);
        exp = e;
    }

    @Override
    public void verifier() {
        try { //On vérifie qu'on est bien dans une fonction
            if (LFCT.getInstance().getDebut()) {
                throw new ReturnException(noLigne, "Retourne hors d'une fonction");
            }
        } catch (ReturnException e) {
            CollectExcept.getInstance().addException(e);
        }
        //On cherche la fonction dans laquelle on est
        Iterator<Fonction> it = LFCT.getInstance().getIterator();
        int reste = Integer.MIN_VALUE; //moins infini
        String nom = "";
        while (it.hasNext()){
            Fonction f = it.next();
            if (f.getNoLigne() - noLigne > reste && f.getNoLigne() - noLigne < 0){
                reste = f.getNoLigne() - noLigne;
                nom = f.getNom();
            }
        }
        //On vérifie que le type de retour de la fonction est le même que celui de l'expression
        try {
            Symbole s = TDS.getInstance().identifier(new EntreeFonction(nom,0,0)); //ici les 0 n'ont pas d'importance
            if (!s.getType().equals(exp.getType())) {
                throw new ReturnException(noLigne, "Le type de retour de la fonction ne correspond pas au type de l'expression");
            }
        } catch (ReturnException e) {
            CollectExcept.getInstance().addException(e);
        }

        exp.verifier();
    }

    @Override
    public String toMIPS() {
        String mips = "#Retourne\n";
        if (!exp.isFonc()) {
            if (exp.getType().equals("booleen")) {
                mips += "la $v0, " + exp.toMIPS() + "\n";
            } else {
                mips += "li $v0, " + exp.toMIPS() + "\n";
            }
        } else {
            mips += exp.toMIPS();
        }
        mips += "jr $ra\n";

        return mips;
    }

    @Override
    public boolean isReturn() {
        return true;
    }
}
