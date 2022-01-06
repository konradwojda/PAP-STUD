package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.PatternStop;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;

public class PatternStopCellController extends ListCell<PatternStop> {
    @Override
    public void updateItem(PatternStop patternStop, boolean empty) {
        super.updateItem(patternStop, empty);
        if (patternStop != null) {
            PatternStopCell patternStopCell = new PatternStopCell();
            // TODO:
            patternStop.setStop(patternStopCell.getChoiceStop().getValue());
            // tu trzeba policzyć
            patternStop.setTravelTime(100);
            // patternViewController.setIndex("1");
            // patternViewController.setHour("00");
            // patternViewController.setMinutes("00");

            patternStopCell.setUpButton((ActionEvent event) -> {
                System.out.println("Up ");
                // TODO:
            });

            patternStopCell.setDownButton((ActionEvent event) -> {
                // TODO:
                System.out.println("Down ");
            });

            patternStopCell.setRemovePatternStopButton((ActionEvent event) -> {
                // TODO:
                System.out.println("Remove ");
            });

            setGraphic(patternStopCell.getHboxRoot());
        }
    }
}
