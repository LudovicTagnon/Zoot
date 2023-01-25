package zoot.exceptions;

public class NonDeclarerException extends AnalyseException{

    public  NonDeclarerException(String idf) {
        super("Variable "+idf+" non déclarée !");
    }
}
