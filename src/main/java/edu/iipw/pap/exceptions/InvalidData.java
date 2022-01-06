package edu.iipw.pap.exceptions;

public class InvalidData extends Exception {
    public InvalidData(String arg0) {
        super(arg0);
    }

    public InvalidData(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public InvalidData(Throwable arg0) {
        super(arg0);
    }
}
