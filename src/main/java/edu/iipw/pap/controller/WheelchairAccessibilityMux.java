package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.WheelchairAccessibility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * WheelchairAccessibilityMux is a bridge between
 * two observable boolean properties (one for the 'indeterminate' status and one for 'checked')
 * and a WheelchairAccessibility observable property.
 *
 * Changes to one property are automatically propagated to the other one.
 **/
public class WheelchairAccessibilityMux {
    private BooleanProperty indeterminate = new SimpleBooleanProperty();
    private BooleanProperty checked = new SimpleBooleanProperty();
    private ObjectProperty<WheelchairAccessibility> accessible = new SimpleObjectProperty<>();

    private ChangeListener<Boolean> accessibilitySetter = (ObservableValue<? extends Boolean> observable,
            Boolean oldValue, Boolean newValue) -> {
        setAccessibility();
    };

    private ChangeListener<WheelchairAccessibility> checkboxSetter = (
            ObservableValue<? extends WheelchairAccessibility> observable, WheelchairAccessibility oldValue,
            WheelchairAccessibility newValue) -> {
        setCheckbox();
    };

    public WheelchairAccessibilityMux() {
        indeterminate.addListener(accessibilitySetter);
        checked.addListener(accessibilitySetter);
        accessible.addListener(checkboxSetter);
    }

    private void setCheckbox() {
        switch (accessible.get()) {
            case UNKNOWN:
                indeterminate.set(true);
                break;
            case ACCESSIBLE:
                indeterminate.set(false);
                checked.set(true);
                break;
            case INACCESSIBLE:
                indeterminate.set(false);
                checked.set(false);
                break;
        }
    }

    private void setAccessibility() {
        if (indeterminate.get()) {
            accessible.set(WheelchairAccessibility.UNKNOWN);
        } else if (checked.get()) {
            accessible.set(WheelchairAccessibility.ACCESSIBLE);
        } else {
            accessible.set(WheelchairAccessibility.INACCESSIBLE);
        }
    }

    public BooleanProperty indeterminateProperty() {
        return indeterminate;
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public ObjectProperty<WheelchairAccessibility> accessibleProperty() {
        return accessible;
    }
}
