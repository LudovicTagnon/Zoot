package zoot.arbre.declarations;

import zoot.arbre.ArbreAbstrait;

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
    }

    @Override
    public String toMIPS() {
        //TODO
        return null;
    }
}
