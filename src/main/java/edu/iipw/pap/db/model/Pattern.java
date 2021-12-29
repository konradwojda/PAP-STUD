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

    public Pattern(String headsign_, PatternDirection direction_, Line line_) {
        this.headsign = headsign_;
        this.direction = direction_;
        this.line = line_;
    }
}
