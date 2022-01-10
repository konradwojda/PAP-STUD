package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.PatternStop;
import javafx.scene.control.ListCell;

/**
 * PatternStopCellController is responsible for view of pattern stops in pattern
 */
public class PatternStopCellController extends ListCell<PatternStop> {

    @Override
    public void updateItem(PatternStop patternStop, boolean empty) {
        super.updateItem(patternStop, empty);
        if (patternStop != null && !empty) {
            PatternStopCell patternStopCell = new PatternStopCell();
            try {
                patternStopCell.setObject(patternStop);
                patternStopCell.setObject(this.listViewProperty().get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            setGraphic(patternStopCell.getHboxRoot());
        } else {
            setGraphic(null);
        }
    }
};
