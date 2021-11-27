package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agencies")
public class Agency {

    @Id
    @GeneratedValue
    @Column(name="agency_id")
    private int agencyId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "telephone")
    private String telephone;

    public Agency() {
    }

    public Agency(String name_, String website_, String timezone_, String telephone_) {
        this.name = name_;
        this.website = website_;
        this.timezone = timezone_;
        this.telephone = telephone_;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int newId) {
        agencyId = newId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String newWebsite) {
        website = newWebsite;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String newTimezone) {
        timezone = newTimezone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String newTelephone) {
        telephone = newTelephone;
    }

    public String toString(){
        return this.name;
    }
}
