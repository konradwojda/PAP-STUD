package edu.iipw.pap.exceptions;

/**
 * Exception used when an invalid object type is passed to
 * Controller's setObject() method.
 */
public class InvalidObject extends Exception {
    public InvalidObject(String arg0) {
        super(arg0);
    }

    public InvalidObject(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public InvalidObject(Throwable arg0) {
        super(arg0);
    }
}
