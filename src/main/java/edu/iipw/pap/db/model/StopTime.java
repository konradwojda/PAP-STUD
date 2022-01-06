package edu.iipw.pap.db.model;

import lombok.Value;

/**
 * StopTime is a immutable entity representing
 * a particular trip's departure from a particular trip.
 */
@Value
public class StopTime {
    /**
     * Trip to which this StopTime belongs.
     */
    private Trip trip;

    /**
     * Stop from which the departure commences.
     */
    private Stop stop;

    /**
     * All stoppages of a trip (and pattern) are assumed to be
     * travelled in the order specified by this field.
     */
    private int index;

    /**
     * Represents the actual time of departure from this stop of this trip.
     * Stored as the offset from midnight - can't be negative, but can exceed 24 hours.
     */
    private int departureTime;

    /**
     * Creates a StopTime for a Trip at a particular PatternStop.
     */
    public static StopTime tripAtPatternStop(Trip t, PatternStop ps) {
        assert t.getPattern().equals(ps.getPattern());
        return new StopTime(t, ps.getStop(), ps.getIndex(), t.getDeparture() + ps.getTravelTime());
    }
}
