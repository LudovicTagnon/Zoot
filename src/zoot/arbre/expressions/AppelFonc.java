package zoot.arbre.expressions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;
import zoot.arbre.declarations.Entree;
import zoot.arbre.declarations.LFCT;

import java.util.ArrayList;

public class AppelFonc extends Expression{

    //Similaire à Idf
    Entree e;
    Symbole s;
    ArrayList<Expression> params; //Paramètres d'une fonction lors de son appel

    public AppelFonc(Entree entree, int n) {
        super(n);
        e = entree;
        params = LFCT.getInstance().destockParamsApl(); //On récupère les paramètres de l'appel de fonction
    }

    @Override
    public void verifier() {
        s = TDS.getInstance().identifier(e);
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
            for (Expression param : params) {
                cpt--;
                mips += param.toMIPS();
                mips += "sw $v0,"+cpt+"($sp)\n"; //Sauvegarde du registre $v0
                mips += "addi $sp,$sp,-4\n"; //Décalage du pointeur de pile
            }
            mips += "addi $sp,$sp,"+params.size()+"\n"; //Décalage du pointeur de pile
        }

        mips += "\n#Saut vers une fonction";
        mips += "\njal "+e.getNom()+"_"+e.getNoLigne()+"\n";
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
