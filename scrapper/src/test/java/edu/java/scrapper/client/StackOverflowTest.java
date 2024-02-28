package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.service.StackOverflowClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScrapperApplication.class)
public class StackOverflowTest {
    @Autowired
    StackOverflowClientService stackOverflowClientService;
    private WireMockServer wireMockServer;

    private final OffsetDateTime lastActivityTime = OffsetDateTime.of(
        2017, 11 , 30,0, 0, 0, 0, ZoneOffset.UTC
    );

    @BeforeEach
    void init() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @AfterEach
    void destroy() {
        wireMockServer.stop();
    }

    @Test
    void testFetchStackOverflowRepository() {
        wireMockServer.stubFor(
            get("questions/30792268")
                .willReturn(ok()
                    .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(
                        """
                                {
                                     "items": [
                                         {
                                             "owner": {
                                                 "display_name": "newbie",
                                                 "user_id": 708489
                                             },
                                             "question_id": 30792268,
                                             "last_activity_date": "%s",
                                         }
                                     ]
                                 }
                                """.formatted(lastActivityTime.toString())
                    )
                )
        );
        stackOverflowClientService.fetchStackOverflowUserResponse(30792268L).subscribe(
            client ->{
                assertThat(client.questionsResponses().get(0).questionId()).isEqualTo(30792268L);
                assertThat(client.questionsResponses().get(0).owner().displayName()).isEqualTo("newbie");
                assertThat(client.questionsResponses().get(0).lastActivityDate().toString())
                    .isEqualTo(lastActivityTime.toString());
            }
        );
    }
}
