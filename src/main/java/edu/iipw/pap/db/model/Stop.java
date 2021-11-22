package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stops")
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

    @Column(name = "wheelchair_accessible")
    private WheelchairAccessibility wheelchairAccessible;

    public Stop() {
    }

    public Stop(String name_, String code_, double lat_, double lon_, WheelchairAccessibility wheelchairAccessible_) {
        this.name = name_;
        this.code = code_;
        this.lat = lat_;
        this.lon = lon_;
        this.wheelchairAccessible = wheelchairAccessible_;
    }

    public int getStopId() {
        return this.stopId;
    }

    public void setStopId(int newId) {
        this.stopId = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String newCode) {
        this.code = newCode;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double newLat) {
        this.lat = newLat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double newLon) {
        this.lon = newLon;
    }

    public WheelchairAccessibility isWheelchairAccessible() {
        return this.wheelchairAccessible;
    }

    public void setWheelchairAccessible(WheelchairAccessibility newAccessibility) {
        this.wheelchairAccessible = newAccessibility;
    }
}
