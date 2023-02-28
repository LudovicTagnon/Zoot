package zoot.exceptions;

public class ReturnException extends AnalyseException {

    public ReturnException(int numLigne, String message) {
        super("Erreur ligne " + numLigne + " : "+message);
    }
}
