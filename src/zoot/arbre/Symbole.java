package zoot.arbre;

public class Symbole {
    private int decalage ;
    private String type ;

    public Symbole(String t) {
        type = t ;
    }


    // Getters and setters


    public int getDecalage() {
        return decalage;
    }

    public void setDecalage(int decalage) {
        this.decalage = decalage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
