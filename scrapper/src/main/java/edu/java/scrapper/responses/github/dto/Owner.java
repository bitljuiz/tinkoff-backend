package edu.java.scrapper.responses.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Owner(
    @JsonProperty("id") @NotNull Long id,
    @JsonProperty("login") @NotBlank String login
) {
}
