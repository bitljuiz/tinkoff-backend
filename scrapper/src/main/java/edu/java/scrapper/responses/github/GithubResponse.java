package edu.java.scrapper.responses.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.responses.github.dto.Owner;
import java.time.OffsetDateTime;

public record GithubResponse(
    @JsonProperty("owner") Owner owner,
    @JsonProperty("pushed_at") OffsetDateTime pushTime,
    @JsonProperty("updated_at") OffsetDateTime updateTime
) {
}
