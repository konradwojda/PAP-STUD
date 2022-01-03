package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.PatternStop;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;

public class PatternStopCell extends ListCell<PatternStop> {
    @Override
    public void updateItem(PatternStop patternStop, boolean empty) {
        super.updateItem(patternStop, empty);
        if (patternStop != null) {
            PatternViewController patternViewController = new PatternViewController();
            // TODO:
            patternViewController.setIndex("1");
            patternViewController.setHour("00");
            patternViewController.setMinutes("00");

            patternViewController.setUpButton((ActionEvent event) -> {
                System.out.println("Up ");
                // TODO:
            });

            patternViewController.setDownButton((ActionEvent event) -> {
                // TODO:
                System.out.println("Down ");
            });

            patternViewController.setRemovePatternStopButton((ActionEvent event) -> {
                // TODO:
                System.out.println("Remove ");
            });

            setGraphic(patternViewController.getHboxRoot());
        }
    }
}
