package com.sourav.companysearch.application.model;

import java.util.List;


/**
 * Represents the response from the TruProxy API when fetching company officers.
 * 
 * This class encapsulates the list of officers associated with a company, as returned by the API.
 * 
 * @author Sourav Bhattacharya
 */
public class OfficerResponse {
    private List<Officer> items;

    // Getter and setter
    public List<Officer> getItems() {
        return items;
    }

    public void setItems(List<Officer> items) {
        this.items = items;
    }
}