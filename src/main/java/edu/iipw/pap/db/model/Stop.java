package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stops")
@NoArgsConstructor
@Data
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "stop_id")
    private int stopId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "lat", nullable = false)
    private double lat;

    @Column(name = "lon", nullable = false)
    private double lon;

    @Column(name = "wheelchair_accessible", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private WheelchairAccessibility wheelchairAccessible = WheelchairAccessibility.UNKNOWN;

    public Stop(String name_, String code_, double lat_, double lon_, WheelchairAccessibility wheelchairAccessible_) {
        this.name = name_;
        this.code = code_;
        this.lat = lat_;
        this.lon = lon_;
        this.wheelchairAccessible = wheelchairAccessible_;
    }
}
