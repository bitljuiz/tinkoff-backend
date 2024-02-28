package edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private final ApplicationConfig applicationConfig;

    public ClientConfiguration(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public WebClient githubClient() {
        return WebClient.create(applicationConfig.clientBaseUrl().github());
    }

    @Bean
    public WebClient stackOverflowClient() {
        return WebClient.create(applicationConfig.clientBaseUrl().stackoverflow());
    }
}
