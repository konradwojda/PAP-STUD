package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.Trip;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;

public class TripCellControler extends ListCell<Trip>{
    @Override
    public void updateItem(Trip trip, boolean empty) {
        super.updateItem(trip, empty);
        if (trip != null) {
            TripCell tripCell= new TripCell();
            try {
                tripCell.setObject(trip);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            setGraphic(tripCell.getHboxRoot());
        }
    }
}
