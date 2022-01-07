package edu.iipw.pap.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class StrIntMux {
    private StringProperty s = new SimpleStringProperty();
    private IntegerProperty i = new SimpleIntegerProperty();

    private ChangeListener<Number> sSetter = (ObservableValue<? extends Number> observable,
            Number oldValue, Number newValue) -> {
        setS();
    };

    private ChangeListener<String> iSetter = (
            ObservableValue<? extends String> observable, String oldValue,
            String newValue) -> {
        setI();
    };

    public StrIntMux() {
        s.addListener(iSetter);
        i.addListener(sSetter);
    }

    private void setS() {
        s.set(String.format("%d", i.get()));
    }

    private void setI() {
        i.set(Integer.parseInt(s.get()));
    }

    public StringProperty sProperty() {
        return s;
    }

    public IntegerProperty iProperty() {
        return i;
    }

}
