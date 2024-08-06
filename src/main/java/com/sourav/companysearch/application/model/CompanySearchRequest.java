package com.sourav.companysearch.application.model;

/**
 * Represents a request to search for companies.
 * 
 * This class encapsulates the criteria used to search for companies, 
 * either by company name or company number.
 * 
 * @author Sourav Bhattacharya
 */
public class CompanySearchRequest {

    private String companyName;      // The name of the company to search for (optional)
    private String companyNumber;    // The registration number of the company to search for (optional)

    /**
     * Default constructor (no arguments).
     */
    public CompanySearchRequest() {
        // You can initialize fields to default values here if needed
    }

    /**
     * Constructor to create a CompanySearchRequest with both company name and number.
     * 
     * @param companyName    The name of the company.
     * @param companyNumber  The registration number of the company.
     */
    public CompanySearchRequest(String companyName, String companyNumber) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
    }

    // Getters and setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
}
