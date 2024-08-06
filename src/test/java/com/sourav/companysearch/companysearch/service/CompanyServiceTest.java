package com.sourav.companysearch.companysearch.service;


import com.sourav.companysearch.application.model.*;
import com.sourav.companysearch.application.service.CompanyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test class for CompanyService.
 * These tests cover various scenarios for company searches, including happy path,
 * inactive companies, companies with no officers, and API errors.
 */
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class CompanyServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Value("${truproxyapi.api-key}")
    private String apiKey;

    private CompanyService companyService;

    /**
     * Set up the test environment before each test.
     * This includes mocking the WebClient.Builder and creating a new CompanyService instance.
     */
    @BeforeEach
    void setUp() {
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        companyService = new CompanyService(webClientBuilder, "http://api.example.com");
    }

    /**
     * Test the happy path scenario where a company is found and enriched with officer data.
     * This test verifies that:
     * 1. The company details are correctly returned
     * 2. The company is enriched with officer data
     * 3. The API is called the correct number of times with the correct parameters
     */
    @Test
    void searchCompanies_shouldReturnEnrichedCompanyResponse() {
        // Arrange
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("BBC LIMITED");
        request.setCompanyNumber("06500244");

        Company company = new Company();
        company.setCompanyNumber("06500244");
        company.setTitle("BBC LIMITED");
        company.setCompanyStatus("active");

        CompanySearchResponse searchResponse = new CompanySearchResponse();
        searchResponse.setItems(List.of(company));

        OfficerResponse officerResponse = new OfficerResponse();
        Officer officer1 = new Officer();
        officer1.setName("John Smith");
        officer1.setOfficerRole("Director");
        Officer officer2 = new Officer();
        officer2.setName("Jane Doe");
        officer2.setOfficerRole("Secretary");
        officerResponse.setItems(List.of(officer1, officer2));

        setupWebClientMock(searchResponse, officerResponse);

        // Act
        CompanySearchResponse result = companyService.searchCompanies(request, true, apiKey);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalResults());
        assertEquals(1, result.getItems().size());
        Company resultCompany = result.getItems().get(0);
        assertEquals("06500244", resultCompany.getCompanyNumber());
        assertEquals("BBC LIMITED", resultCompany.getTitle());
        assertEquals("active", resultCompany.getCompanyStatus());
        assertEquals(2, resultCompany.getOfficers().size());
        assertEquals("John Smith", resultCompany.getOfficers().get(0).getName());
        assertEquals("Director", resultCompany.getOfficers().get(0).getOfficerRole());
        assertEquals("Jane Doe", resultCompany.getOfficers().get(1).getName());
        assertEquals("Secretary", resultCompany.getOfficers().get(1).getOfficerRole());

        verify(webClient, times(2)).get();
        verify(requestHeadersSpec, times(2)).header(eq("x-api-key"), eq(apiKey));
    }

    /**
     * Test the scenario where an inactive company is filtered out when activeOnly is true.
     * This test verifies that:
     * 1. Inactive companies are not included in the result when activeOnly is true
     * 2. The API is called only once (for company search, not for officer data)
     */
    @Test
    void searchCompanies_withInactiveCompany_shouldFilterOutCompany() {
        // TODO
    }

      /**
     * Test the error handling when the API returns an error.
     * This test verifies that:
     * 1. When the API returns an error, a CompanySearchResponse with an empty list is returned
     * 2. The API is called only once before the error is encountered
     */
    @Test
    void searchCompanies_withApiError_shouldReturnResponseWithEmptyList() {
        // TODO 
        
    }

    /**
     * Test the error handling when the API returns an error.
     * This test verifies that:
     * 1. When the API returns an error, an empty response is returned
     * 2. The API is called only once before the error is encountered
     */
    @Test
    void searchCompanies_withApiError_shouldReturnEmptyResponse() {
        // TODO
    }

    /**
     * Helper method to set up the WebClient mock for both company search and officer search.
     * This method reduces code duplication in the test cases.
     *
     * @param searchResponse The mocked company search response
     * @param officerResponse The mocked officer search response
     */
    private void setupWebClientMock(CompanySearchResponse searchResponse, OfficerResponse officerResponse) {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(eq("x-api-key"), eq(apiKey))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CompanySearchResponse.class)).thenReturn(Mono.just(searchResponse));
        when(responseSpec.bodyToMono(OfficerResponse.class)).thenReturn(Mono.just(officerResponse));
    }
}