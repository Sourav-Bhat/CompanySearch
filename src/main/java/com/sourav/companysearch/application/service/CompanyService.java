package com.sourav.companysearch.application.service;

import com.sourav.companysearch.application.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling company search operations.
 * This class interacts with the external API to fetch company and officer data.
 *
 * @author Sourav Bhattacharya
 */
@Service
public class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    
    private final WebClient webClient;

    /**
     * Constructor for CompanyService.
     * 
     * @param webClientBuilder WebClient.Builder to create the WebClient
     * @param baseUrl Base URL for the API, injected from application properties
     */
    @Autowired
    public CompanyService(WebClient.Builder webClientBuilder,
                          @Value("${truproxyapi.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    /**
     * Searches for companies based on the provided request.
     * 
     * @param request The search request containing company name or number
     * @param activeOnly Flag to filter only active companies
     * @param apiKey The API key for authentication
     * @return CompanySearchResponse containing the search results
     */
    public CompanySearchResponse searchCompanies(CompanySearchRequest request, boolean activeOnly, String apiKey) {
        String searchTerm = request.getCompanyNumber() != null ? request.getCompanyNumber() : request.getCompanyName();
        
        CompanySearchResponse searchResponse = callCompanySearchApi(searchTerm, apiKey);
        logger.info("Search response: {}", searchResponse);
        
        if (searchResponse != null && searchResponse.getItems() != null) {
            List<Company> filteredCompanies = searchResponse.getItems().stream()
                    .filter(company -> !activeOnly || "active".equalsIgnoreCase(company.getCompanyStatus()))
                    .map(company -> enrichCompanyWithOfficers(company, apiKey))
                    .collect(Collectors.toList());

            searchResponse.setItems(filteredCompanies);
            searchResponse.setTotalResults(filteredCompanies.size());
        }

        logger.info("Final response: {}", searchResponse);
        return searchResponse;
    }

    /**
     * Calls the company search API.
     * 
     * @param searchTerm The term to search for
     * @param apiKey The API key for authentication
     * @return CompanySearchResponse containing the API response
     */
    private CompanySearchResponse callCompanySearchApi(String searchTerm, String apiKey) {
        try {
            CompanySearchResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/Search")
                            .queryParam("Query", searchTerm)
                            .build())
                    .header("x-api-key", apiKey)
                    .retrieve()
                    .bodyToMono(CompanySearchResponse.class)
                    .block();
            logger.info("API response: {}", response);
            return response;
        } catch (WebClientResponseException e) {
            logger.error("Error calling Company Search API: {}", e.getMessage());
            return new CompanySearchResponse();
        }
    }

    /**
     * Enriches a company with its officers' information.
     * 
     * @param company The company to enrich
     * @param apiKey The API key for authentication
     * @return The company enriched with officers' information
     */
    private Company enrichCompanyWithOfficers(Company company, String apiKey) {
        try {
            OfficerResponse officerResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/Officers")
                            .queryParam("CompanyNumber", company.getCompanyNumber())
                            .build())
                    .header("x-api-key", apiKey)
                    .retrieve()
                    .bodyToMono(OfficerResponse.class)
                    .block();

            logger.info("Officer response for company {}: {}", company.getCompanyNumber(), officerResponse);

            if (officerResponse != null && officerResponse.getItems() != null) {
                List<Officer> activeOfficers = officerResponse.getItems().stream()
                        .filter(officer -> officer.getResignedOn() == null)
                        .collect(Collectors.toList());
                company.setOfficers(activeOfficers);
            } else {
                company.setOfficers(Collections.emptyList());
            }
        } catch (WebClientResponseException e) {
            logger.error("Error fetching officers for company {}: {}", company.getCompanyNumber(), e.getMessage());
            company.setOfficers(Collections.emptyList());
        }

        return company;
    }
}