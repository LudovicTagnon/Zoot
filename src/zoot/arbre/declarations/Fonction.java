package zoot.arbre.declarations;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.Symbole;
import zoot.exceptions.CollectExcept;
import zoot.exceptions.ReturnException;

import java.util.HashMap;

public class Fonction extends ArbreAbstrait{

    private String nom;
    private ArbreAbstrait instruction;
    private String label;

    private HashMap<Entree, Symbole> params;

    public Fonction(String idf, ArbreAbstrait inst, int n) {
        super(n);
        nom = idf;
        instruction = inst;
        params = LFCT.getInstance().destockParams();
        label = idf + "_" + params.size();
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
}
