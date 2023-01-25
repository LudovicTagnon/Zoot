package zoot.exceptions;

public class DoubleDeclarationException extends AnalyseException{
    public DoubleDeclarationException(String idf) {
        super("Variable "+idf+" déclarée plusieurs fois !");
    }
}
