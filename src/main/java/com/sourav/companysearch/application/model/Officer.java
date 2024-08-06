package com.sourav.companysearch.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents an officer (director, secretary, etc.) of a company.
 * 
 * This class models the officer information as returned by the TruProxy API.
 * 
 * @author Sourav Bhattacharya
 */
public class Officer {
    private String name;
    
    @JsonProperty("officer_role")
    private String officerRole;
    
    @JsonProperty("appointed_on")
    private String appointedOn;
    
    @JsonProperty("resigned_on")
    private String resignedOn;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficerRole() {
        return officerRole;
    }

    public void setOfficerRole(String officerRole) {
        this.officerRole = officerRole;
    }

    public String getAppointedOn() {
        return appointedOn;
    }

    public void setAppointedOn(String appointedOn) {
        this.appointedOn = appointedOn;
    }

    public String getResignedOn() {
        return resignedOn;
    }

    public void setResignedOn(String resignedOn) {
        this.resignedOn = resignedOn;
    }


     /**
     * Generates a string representation of the Officer object for debugging purposes.
     * 
     * @return A string containing the officer's name, role, and appointment date.
     */
    @Override
    public String toString() {
        return "Officer{" +
                "name='" + name + '\'' +
                ", officerRole='" + officerRole + '\'' +
                ", appointedOn='" + appointedOn + '\'' +
                ", resignedOn='" + resignedOn + '\'' +
                '}';
    }
}