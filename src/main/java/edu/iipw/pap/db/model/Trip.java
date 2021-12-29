package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trips")
@NoArgsConstructor
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "trip_id")
    private int tripId;

    @Column(name = "departure")
    private int departure;

    @ManyToOne
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @Column(name = "wheelchair_accessible", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private WheelchairAccessibility wheelchairAccessible = WheelchairAccessibility.UNKNOWN;

    public Trip(int departure_, Pattern pattern_, Calendar calendar_, WheelchairAccessibility wheelchairAccessible_) {
        this.departure = departure_;
        this.pattern = pattern_;
        this.calendar = calendar_;
        this.wheelchairAccessible = wheelchairAccessible_;
    }

}
