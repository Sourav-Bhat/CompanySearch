package com.sourav.companysearch.application.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration   
 class for creating and configuring a WebClient bean.
 * 
 * This class is responsible for setting up a WebClient instance that will be used to make 
 * HTTP requests to the TruProxy API. It reads the base URL from the application properties 
 * and configures the WebClient with this base URL. 
 * 
 * @author Sourav Bhattacharya
 */

@Configuration // Indicates this is a Spring configuration class
public class WebClientConfig {

    @Value("${truproxyapi.base-url}") // Inject the base URL from application.properties
    private String baseUrl;

    /**
     * Creates and configures a WebClient bean.
     * 
     * @return A configured WebClient instance.
     */
    @Bean // Declares this method as a Spring bean producer
    public WebClient webClient() {
        return WebClient.builder() // Create a WebClient builder
                .baseUrl(baseUrl)   // Set the base URL for API requests
                .build();           // Build the WebClient instance
    }
}