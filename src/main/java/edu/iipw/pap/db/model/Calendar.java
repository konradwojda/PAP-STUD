package edu.iipw.pap.db.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.iipw.pap.db.typeConverters.BooleanConverter;
import edu.iipw.pap.db.typeConverters.LocalDateConverter;
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
    @Convert(converter = LocalDateConverter.class)
    private LocalDate start;

    @Column(name = "end")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate end;

    @Column(name = "monday")
    @Convert(converter = BooleanConverter.class)
    private boolean monday = false;

    @Column(name = "tuesday")
    @Convert(converter = BooleanConverter.class)
    private boolean tuesday = false;

    @Column(name = "wednesday")
    @Convert(converter = BooleanConverter.class)
    private boolean wednesday = false;

    @Column(name = "thursday")
    @Convert(converter = BooleanConverter.class)
    private boolean thursday = false;

    @Column(name = "friday")
    @Convert(converter = BooleanConverter.class)
    private boolean friday = false;

    @Column(name = "saturday")
    @Convert(converter = BooleanConverter.class)
    private boolean saturday = false;

    @Column(name = "sunday")
    @Convert(converter = BooleanConverter.class)
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
