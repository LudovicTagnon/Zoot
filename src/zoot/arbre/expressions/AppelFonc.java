package zoot.arbre.expressions;

import zoot.arbre.Symbole;
import zoot.arbre.TDS;
import zoot.arbre.declarations.Entree;

public class AppelFonc extends Expression{

    //Similaire à Idf
    Entree e;
    Symbole s;

    public AppelFonc(Entree entree, int n) {
        super(n);
        e = entree;
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
        mips += "\n#Saut vers une fonction";
        mips += "\njal "+e.getNom()+"\n";
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
