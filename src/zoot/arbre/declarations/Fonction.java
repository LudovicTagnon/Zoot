package zoot.arbre.declarations;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.Symbole;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.ReturnException;

import java.util.HashMap;
import java.util.Map;

public class Fonction extends ArbreAbstrait{

    private String nom;
    private ArbreAbstrait instruction;
    private String label;

    private HashMap<Entree, Symbole> params;
    private int bloc;

    public Fonction(String idf, ArbreAbstrait inst, int b, int n) {
        super(n);
        nom = idf;
        instruction = inst;
        params = LFCT.getInstance().destockParams();
        label = idf + "_" + params.size();
        bloc = b;
    }

    @Override
    public void verifier() {
        instruction.verifier();

        boolean retourne = false;
        BlocDInstructions inst = (BlocDInstructions) instruction;
        for (ArbreAbstrait i : inst.getInstructions()){
            if (i.isReturn()){
                retourne = true;
            }
        }
        try {
            if (!retourne) {
                throw new ReturnException(noLigne, "Pas de retour dans la fonction " + nom);
            }
        } catch (ReturnException e) {
            CollectExcept.getInstance().addException(e);
        }
    }

    @Override
    public String toMIPS() {
        String mips = "\n" + label + ":\n";

        int bornInf = Integer.MAX_VALUE; //infinit
        for (Symbole symbole : params.values()){ //on cherche le décalage le plus petit
            if (symbole.getDecalage() < bornInf){
                bornInf = symbole.getDecalage();
            }
        }

        for (int i = 0; i < params.size(); i++) { //on stocke les paramètres dans la pile
            mips += "lw $v0, 4($sp)\n";
            mips += "addi $sp,$sp, 4\n";
            mips += "sw $v0, " + bornInf + "($s7)\n";
            bornInf += 4;
        }

        mips += instruction.toMIPS();
        return mips;
    }

    public String getNom(){
        return nom;
    }

    public String getLabel(){
        return label;
    }

    public int getNbParams(){
        return params.size();
    }

    public int getBloc(){
        return bloc;
    }
}
