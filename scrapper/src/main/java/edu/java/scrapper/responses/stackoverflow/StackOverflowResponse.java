package edu.java.scrapper.responses.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.scrapper.responses.stackoverflow.dto.Question;
import java.util.List;

public record StackOverflowResponse(@JsonProperty("items") List<Question> questionsResponses) {
}
