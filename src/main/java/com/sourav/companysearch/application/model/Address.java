package com.sourav.companysearch.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Represents an address associated with a company or officer.
 * This class models the address information as returned by the TruProxy API.
 * 
 * @author Sourav Bhattacharya
 */
public class Address {
    private String premises;
    @JsonProperty("address_line_1") // Maps to the "address_line_1" property in the API response
    private String addressLine1;
    private String locality;
    private String country;
    @JsonProperty("postal_code")  // Maps to the "postal_code" property in the API response
    private String postalCode;

   // Getters and setters
    public String getPremises() {
        return premises;
    }
    public void setPremises(String premises) {
        this.premises = premises;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getLocality() {
        return locality;
    }
    public void setLocality(String locality) {
        this.locality = locality;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

 

    @Override
    public String toString() {
        return "Address{" +
                "premises='" + premises + '\'' +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}