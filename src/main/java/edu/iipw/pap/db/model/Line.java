package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lines")
public class Line {

    @Id
    @GeneratedValue
    @Column(name = "line_id")
    private int lineId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private LineType type;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    public Line() {
    }

    public Line(String code_, String description_, LineType type_, Agency agency_) {
        this.code = code_;
        this.description = description_;
        this.type = type_;
        this.agency = agency_;
    }

    public int getLineId() {
        return this.lineId;
    }

    public void setLineId(int newId) {
        this.lineId = newId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String newCode) {
        this.code = newCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public LineType getType() {
        return this.type;
    }

    public void setType(LineType newType) {
        this.type = newType;
    }

    public Agency getAgency() {
        return this.agency;
    }

    public void setAgency(Agency newAgency) {
        this.agency = newAgency;
    }
}
