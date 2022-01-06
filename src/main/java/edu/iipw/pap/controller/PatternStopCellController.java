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

            setGraphic(patternStopCell.getHboxRoot());
        }
    }
}
