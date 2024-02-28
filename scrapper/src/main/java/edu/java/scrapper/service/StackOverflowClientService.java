package edu.java.scrapper.service;

import edu.java.scrapper.responses.stackoverflow.StackOverflowResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StackOverflowClientService {
    private final WebClient stackOverflowClient;

    public StackOverflowClientService(WebClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    public Mono<StackOverflowResponse> fetchStackOverflowUserResponse(@NotNull Long questionId) {
        return stackOverflowClient
            .get()
            .uri(questionId + "/" + "?site=stackoverflow")
            .retrieve()
            .bodyToMono(StackOverflowResponse.class);
    }
}
