package com.sourav.companysearch.application.controller;

import com.sourav.companysearch.application.model.CompanySearchRequest;
import com.sourav.companysearch.application.model.CompanySearchResponse;
import com.sourav.companysearch.application.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling company search requests.
 * This class exposes the API endpoint for searching companies.
 *
 * @author Sourav Bhattacharya
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    // Service for handling company search operations
    private final CompanyService companyService;

    // API key for authentication with the external API
    private final String apiKey;

    /**
     * Constructor for CompanyController.
     * 
     * @param companyService The service to handle company search operations
     * @param apiKey The API key for authentication, injected from application properties
     */
    @Autowired
    public CompanyController(CompanyService companyService, @Value("${truproxyapi.api-key}") String apiKey) {
        this.companyService = companyService;
        this.apiKey = apiKey;
    }

    /**
     * Endpoint for searching companies.
     * 
     * @param request The search request containing company name or number
     * @param activeOnly Flag to filter only active companies
     * @return ResponseEntity containing the search results
     */
    @PostMapping("/search")
    public ResponseEntity<CompanySearchResponse> searchCompanies(
            @RequestBody CompanySearchRequest request,
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        // Log the incoming request
        logger.info("Received search request: {}, activeOnly: {}", request, activeOnly);
        
        // Call the service to perform the search
        CompanySearchResponse response = companyService.searchCompanies(request, activeOnly, apiKey);
        
        // Log the completion of the search
        logger.info("Search completed. Total results: {}", response.getTotalResults());

        // Return the response with OK status
        return ResponseEntity.ok(response);
    }
}