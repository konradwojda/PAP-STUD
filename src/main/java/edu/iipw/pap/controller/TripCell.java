package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.Trip;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;

public class TripCell extends ListCell<Trip>{
    @Override
    public void updateItem(Trip trip, boolean empty) {
        super.updateItem(trip, empty);
        if (trip != null) {
            TripViewController tripViewController= new TripViewController();
            // TODO:
            tripViewController.setHour("12");
            tripViewController.setMinutes("23");

            tripViewController.setRemovePatternStopButton((ActionEvent event) -> {
                // TODO:
                System.out.println("Remove ");
            });

            setGraphic(tripViewController.getHboxRoot());
        }
    }
}
