package edu.iipw.pap.db.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "calendars")
@NoArgsConstructor
@Data
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "calendar_id")
    private int calendarId;

    @Column(name = "name")
    private String name;

    @Column(name = "start", nullable = false)
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "monday")
    private boolean monday = false;

    @Column(name = "tuesday")
    private boolean tuesday = false;

    @Column(name = "wednesday")
    private boolean wednesday = false;

    @Column(name = "thursday")
    private boolean thursday = false;

    @Column(name = "friday")
    private boolean friday = false;

    @Column(name = "saturday")
    private boolean saturday = false;

    @Column(name = "sunday")
    private boolean sunday = false;

    public Calendar(String name_, LocalDate start_, LocalDate end_, boolean mon_, boolean tue_, boolean wed_,
            boolean thu_, boolean fri_, boolean sat_, boolean sun_) {
        this.name = name_;
        this.start = start_;
        this.end = end_;
        this.monday = mon_;
        this.tuesday = tue_;
        this.wednesday = wed_;
        this.thursday = thu_;
        this.friday = fri_;
        this.saturday = sat_;
        this.sunday = sun_;
    }
}
