package zoot.arbre.declarations;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.instructions.Instruction;
import zoot.exceptions.ReturnException;

public class Fonction extends ArbreAbstrait{

    private String nom;
    private ArbreAbstrait instruction;

    public Fonction(String idf, ArbreAbstrait inst, int n) {
        super(n);
        nom = idf;
        instruction = inst;
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

        if (!retourne){
            throw new ReturnException(noLigne, "Pas de retour dans la fonction " + nom);
        }
    }

    @Override
    public String toMIPS() {
        String mips = "\n" + nom + ":\n";
        mips += instruction.toMIPS();
        return mips;
    }

    public String getNom(){
        return nom;
    }
}
