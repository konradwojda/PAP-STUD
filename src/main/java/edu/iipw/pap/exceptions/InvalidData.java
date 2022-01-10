package edu.iipw.pap.exceptions;

/**
 * InvalidData is an exception thrown by the model
 * when semantically incorrect data is detected.
 */
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
