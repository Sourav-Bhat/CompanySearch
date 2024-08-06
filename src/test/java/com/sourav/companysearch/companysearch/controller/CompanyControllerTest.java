package com.sourav.companysearch.companysearch.controller;


import com.sourav.companysearch.application.controller.CompanyController;
import com.sourav.companysearch.application.model.CompanySearchRequest;
import com.sourav.companysearch.application.model.CompanySearchResponse;
import com.sourav.companysearch.application.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for CompanyController.
 * This class contains unit tests for the CompanyController, focusing on the searchCompanies endpoint.
 * 
 * @author Sourav Bhattacharya
 */
class CompanyControllerTest {

    /**
     * Mock of the CompanyService. This allows us to control the behavior of the service in our tests.
     */
    @Mock
    private CompanyService companyService;

    /**
     * The CompanyController instance being tested. The @InjectMocks annotation tells Mockito to inject
     * the mock CompanyService into this controller.
     */
    @InjectMocks
    private CompanyController companyController;

    /**
     * A test API key used for the controller.
     */
    private final String apiKey = "test-api-key";

    /**
     * Setup method run before each test.
     * This method initializes the mocks and creates a new instance of the controller for each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize mocks created above
        MockitoAnnotations.openMocks(this);
        // Create a new CompanyController instance for each test
        companyController = new CompanyController(companyService, apiKey);
    }

    /**
     * Test case for the searchCompanies method.
     * This test verifies that the controller correctly processes a search request and returns the expected response.
     */
    @Test
    void testSearchCompanies() {
        // Arrange
        // Create a sample CompanySearchRequest
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("BBC LIMITED");
        boolean activeOnly = false;

        // Create a mock CompanySearchResponse
        CompanySearchResponse mockResponse = new CompanySearchResponse();
        mockResponse.setTotalResults(1);

        // Configure the mock service to return the mock response when called with the specified parameters
        when(companyService.searchCompanies(request, activeOnly, apiKey)).thenReturn(mockResponse);

        // Act
        // Call the controller method being tested
        ResponseEntity<CompanySearchResponse> responseEntity = companyController.searchCompanies(request, activeOnly);

        // Assert
        // Verify that the response status is OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Verify that the response body matches our mock response
        assertEquals(mockResponse, responseEntity.getBody());
        // Verify that the total results in the response body is 1, as set in our mock response
        assertEquals(1, responseEntity.getBody().getTotalResults());

        // Verify that the companyService.searchCompanies method was called exactly once with the correct parameters
        verify(companyService, times(1)).searchCompanies(request, activeOnly, apiKey);
    }

    // TODO: Add more test cases to cover different scenarios, such as:
    // - Testing with activeOnly set to true
    // - Testing with different types of search requests (e.g., by company number)
    // - Testing error conditions (e.g., service throws an exception)
}