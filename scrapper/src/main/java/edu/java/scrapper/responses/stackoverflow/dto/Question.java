package edu.java.scrapper.responses.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record Question(
    @JsonProperty("owner") Owner owner,
    @JsonProperty("question_id") @NotNull Long questionId,
    @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate
    ) {
}
