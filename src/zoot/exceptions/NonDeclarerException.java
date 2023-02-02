package zoot.exceptions;

import zoot.arbre.declarations.Entree;

public class NonDeclarerException extends AnalyseException{

    public  NonDeclarerException(Entree e) {
        super("Variable "+e.toString()+" non déclarée !");
    }
}
