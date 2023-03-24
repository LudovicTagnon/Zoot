package zoot.exceptions;

import java.util.ArrayList;
import java.util.Iterator;

//la classe exception n'a pas vocations à être utilisée en dehors de la classe CollectExcept c'est pourquoi elle est déclarée en private
public class CollectExcept {
    private class exception {
        private String msg;
        private int ligne;

        public exception(int l, String m) {
            this.msg = m;
            this.ligne = l;
        }

        public String getMsg() {
            return msg;
        }

        public int getLigne() {
            return ligne;
        }
    }

    private static CollectExcept instance = new CollectExcept();
    //private ArrayList<exception> exceptions;

    private ArrayList<AnalyseException> exceptions;

    private CollectExcept() {
        exceptions = new ArrayList<>(3);
    }

    public static CollectExcept getInstance() {
        return instance;
    }

    /*public void addException(int l, String m) {
        exceptions.add(new exception(l, m));
    }*/

    public void addException(AnalyseException ex) {
        exceptions.add(ex);
    }

    public int getNbExceptions() {
        return exceptions.size();
    }

    public Iterator<AnalyseException> getIterator() {
        return exceptions.iterator();
    }
/*
    public int getLigne(int i) {
        return exceptions.get(i).getLigne();
    }

    public String getMsg(int i) {
        return exceptions.get(i).getMsg();
    }*/
}
