package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.PatternStop;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;

public class PatternStopCell extends ListCell<PatternStop> {
    @Override
    public void updateItem(PatternStop patternStop, boolean empty) {
        super.updateItem(patternStop, empty);
        if (patternStop != null) {
            PatternViewController patternViewController = new PatternViewController();
            // TODO:
            patternViewController.setIndex(" 00.");
            patternViewController.setHour("12");
            patternViewController.setMinutes("23");

            EventHandler upHandler = (EventHandler) (Event event) -> {
                System.out.println("Up ");
                // TODO:
            };
            patternViewController.setUpButton(upHandler);

            EventHandler downHandler = (EventHandler) (Event event) -> {
                // TODO:
                System.out.println("Down ");
            };
            patternViewController.setDownButton(downHandler);

            EventHandler removeHandler = (EventHandler) (Event event) -> {
                // TODO:
                System.out.println("Remove ");
            };
            patternViewController.setRemovePatternStopButton(removeHandler);

            setGraphic(patternViewController.getHboxRoot());
        }
    }
}
