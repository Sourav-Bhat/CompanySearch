package com.sourav.companysearch.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents the response from a company search.
 * 
 * This class encapsulates the results of a company search, including
 * the total number of results and a list of matching companies.
 *
 * @author Sourav Bhattacharya
 */
public class CompanySearchResponse {


    @JsonProperty("total_results")
    private int totalResults;    // The total number of companies found in the search
    
    private List<Company> items; // The list of companies matching the search criteria

    /**
     * Constructor to create a CompanySearchResponse with total results and a list of companies.
     * 
     * @param totalResults The total number of companies found in the search.
     * @param items The list of companies matching the search criteria.
     */
    public CompanySearchResponse(int totalResults, List<Company> items) {
        this.totalResults = totalResults;
        this.items = items;
    }

    /**
     * Default constructor (no arguments).
     */
    public CompanySearchResponse() {
        // You might want to initialize 'items' to an empty list here to avoid potential null pointer exceptions later
    }

    // Getters and setters
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Company> getItems() {
        return items;
    }

    public void setItems(List<Company> items) {
        this.items = items;
    }

    /**
     * Generates a string representation of the CompanySearchResponse object.
     * 
     * @return A string containing the totalResults and a summary of the items list.
     */
    @Override
    public String toString() {
        return "CompanySearchResponse{" +
                "totalResults=" + totalResults +
                ", items=" + items +
                '}';
    }
}
