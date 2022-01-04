package edu.iipw.pap.db.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "patterns")
@NoArgsConstructor
@Data
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pattern_id")
    private int patternId;

    @Column(name = "headsign")
    private String headsign;

    @Column(name = "direction")
    @Enumerated(EnumType.ORDINAL)
    private PatternDirection direction;

    @ManyToOne
    @JoinColumn(name = "line_id", nullable = false)
    private Line line;

    @OneToMany(mappedBy = "pattern")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PatternStop> patternStops;

    @OneToMany(mappedBy = "pattern")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Trip> trips;
}
