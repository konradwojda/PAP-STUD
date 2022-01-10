package edu.iipw.pap.interfaces;

import edu.iipw.pap.exceptions.InvalidObject;

public interface IController {
    public <T> void setObject(T obj) throws InvalidObject;
}
