package zoot.exceptions;

import zoot.arbre.declarations.Entree;

public class DoubleDeclarationException extends AnalyseException{
    public DoubleDeclarationException(Entree e) {
        super("Variable "+e.toString()+" déclarée plusieurs fois !");
    }
}
