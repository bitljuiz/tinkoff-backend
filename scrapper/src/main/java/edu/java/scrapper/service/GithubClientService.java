package edu.java.scrapper.service;

import edu.java.scrapper.responses.github.GithubResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GithubClientService {
    private final WebClient githubClient;

    public GithubClientService(WebClient githubClient) {
        this.githubClient = githubClient;
    }

    public Mono<GithubResponse> fetchGithubUserResponse(@NotBlank final String user,
        @NotBlank final String repositoryName) {
        return githubClient
            .get()
            .uri(user + "/" + repositoryName)
            .retrieve()
            .bodyToMono(GithubResponse.class);
    }
}
