package com.sourav.companysearch.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;


/**
 * Represents an Company including address and officer deatils.
 * This class models the Company information as returned by the TruProxy API.
 * 
 * @author Sourav Bhattacharya
 */
public class Company {
    @JsonProperty("company_number")
    private String companyNumber;
    
    @JsonProperty("company_status")
    private String companyStatus;
    
    private String title;
    
    @JsonProperty("company_type")
    private String companyType;
    
    @JsonProperty("date_of_creation")
    private String dateOfCreation;
    
    private Address address;
    private List<Officer> officers;

    // Getters and setters for all fields

    public String getCompanyNumber() { return companyNumber; }
    public void setCompanyNumber(String companyNumber) { this.companyNumber = companyNumber; }

    public String getCompanyStatus() { return companyStatus; }
    public void setCompanyStatus(String companyStatus) { this.companyStatus = companyStatus; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompanyType() { return companyType; }
    public void setCompanyType(String companyType) { this.companyType = companyType; }

    public String getDateOfCreation() { return dateOfCreation; }
    public void setDateOfCreation(String dateOfCreation) { this.dateOfCreation = dateOfCreation; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<Officer> getOfficers() { return officers; }
    public void setOfficers(List<Officer> officers) { this.officers = officers; }



    @JsonProperty("address")
    private void unpackAddress(Map<String, Object> address) {
        this.address = new Address();
        this.address.setPremises((String) address.get("premises"));
        this.address.setAddressLine1((String) address.get("address_line_1"));
        this.address.setLocality((String) address.get("locality"));
        this.address.setPostalCode((String) address.get("postal_code"));
        this.address.setCountry((String) address.get("country"));
    }
    @Override
    public String toString() {
        return "Company{" +
                "companyNumber='" + companyNumber + '\'' +
                ", companyStatus='" + companyStatus + '\'' +
                ", title='" + title + '\'' +
                ", companyType='" + companyType + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", address=" + address +
                ", officers=" + officers +
                '}';
    }
}