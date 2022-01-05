package edu.iipw.pap.db.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.iipw.pap.db.typeConverters.BooleanConverter;
import edu.iipw.pap.db.typeConverters.LocalDateConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(name = "start_date", nullable = false)
    @Convert(converter = LocalDateConverter.class)
    private LocalDate start;

    @Column(name = "end_date")
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

    @OneToMany(mappedBy = "calendar")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Trip> trips;
}
