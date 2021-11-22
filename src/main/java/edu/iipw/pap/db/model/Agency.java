package edu.iipw.pap.db.model;

import javax.persistence.Entity;

@Entity
public class Agency {
    private int agencyId;
    private String name;
    private String website;
    private String timezone;
    private String telephone;

    public Agency() {
    }

    public Agency(String n, String www, String tz, String tel) {
        this.name = n;
        this.website = www;
        this.timezone = tz;
        this.telephone = tel;
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
}
