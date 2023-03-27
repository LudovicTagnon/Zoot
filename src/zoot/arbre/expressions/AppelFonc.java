package zoot.arbre.expressions;

import zoot.arbre.SymboleFct;
import zoot.arbre.TDS;
import zoot.arbre.declarations.EntreeFonction;
import zoot.arbre.declarations.LFCT;

import java.util.ArrayList;
import java.util.Collections;

public class AppelFonc extends Expression{

    //Similaire à Idf
    EntreeFonction e;
    SymboleFct s;
    ArrayList<Expression> params; //Paramètres d'une fonction lors de son appel

    public AppelFonc(EntreeFonction entree, int n) {
        super(n);
        e = entree;
        params = LFCT.getInstance().destockParamsApl(); //On récupère les paramètres de l'appel de fonction
    }

    @Override
    public void verifier() {
        s = (SymboleFct) TDS.getInstance().identifier(e);
    }

    @Override
    public String toMIPS() {
        String mips = "#Save registres\n";
        mips += "sw $ra,0($sp)\n"; //Sauvegarde du registre $ra
        mips += "sw $s1,-4($sp)\n"; //Sauvegarde du registre $s1
        mips += "addi $sp,$sp,-8\n"; //Décalage du pointeur de pile

        //Todo: verifier
        if (params.size() > 0) {
            int cpt = params.size(); //Compteur pour les paramètres
            mips += "#Paramètres de l'appel de fonction\n";
            ArrayList<Expression> cpy = new ArrayList<Expression>(params); //Copie de la liste des paramètres
            Collections.reverse(cpy); //On inverse la liste pour avoir les paramètres dans le bon ordre
            for (Expression param : cpy) {
                cpt--;
                if (param.isConstante()){
                    if (param.getType().equals("booleen")) {
                        mips += "la $v0,"+param.toMIPS()+"\n";
                    } else {
                        mips += "li $v0,"+param.toMIPS()+"\n";
                    }
                } else {
                    mips += "lw $v0,"+param.toMIPS()+"\n"; //Chargement de la variable dans le registre $v0
                }
                mips += "sw $v0,"+cpt+"($sp)\n"; //Sauvegarde du registre $v0
            }
            mips += "addi $sp,$sp,"+params.size()*-4+"\n"; //Décalage du pointeur de pile
        }

        mips += "#Saut vers une fonction";
        mips += "\njal "+e.getNom()+"_"+s.getNbParams()+"\n";
        mips += "#Load registres\n";
        mips += "lw $s1,4($sp)\n"; //Restauration du registre $s1
        mips += "lw $ra,8($sp)\n"; //Restauration du registre $ra
        mips += "addi $sp,$sp,8\n"; //Décalage du pointeur de pile
        return mips;
    }

    @Override
    public String getType() {
        return s.getType();
    }

    @Override
    public boolean isFonc() {
        return true;
    }

    @Override
    public String getNom() {
        return e.getNom();
    }
}
