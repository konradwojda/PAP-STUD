package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Agency(String name_, String website_, String timezone_, String telephone_) {
        this.name = name_;
        this.website = website_;
        this.timezone = timezone_;
        this.telephone = telephone_;
    }
}
