package edu.iipw.pap.db.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "agencies")
@NoArgsConstructor
@Data
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "agency_id")
    private int agencyId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(mappedBy = "agency")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Line> lines;
}
