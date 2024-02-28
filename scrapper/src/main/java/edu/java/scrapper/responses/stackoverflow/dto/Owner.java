package edu.java.scrapper.responses.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Owner(
    @JsonProperty("user_id") @NotNull Long id,
    @JsonProperty("display_name") @NotBlank String displayName
) {
}
