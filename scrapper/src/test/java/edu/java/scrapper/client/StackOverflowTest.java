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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScrapperApplication.class)
public class StackOverflowTest {
    @Autowired
    StackOverflowClientService stackOverflowClientService;
    private WireMockServer wireMockServer;

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
//        wireMockServer.stubFor(
//            get("questions/30792268")
//                .willReturn(ok()
//                    .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
//                    .withBody(
//                        """
//                                {
//                                     "items": [
//                                         {
//                                             "owner": {
//                                                 "display_name": "newbie",
//                                                 "user_id": 708489
//                                             },
//                                             "question_id": 30792268,
//                                             "last_activity_date": "2016-01-24T10:15:03Z",
//                                         }
//                                     ]
//                                 }
//                                """
//                    )
//                )
//        );
//        var client = stackOverflowClientService.fetchStackOverflowUserResponse(30792268L).block();
//
//        assertThat(client).isNotNull();
//        assertThat(client.questionsResponses().get(0).questionId()).isEqualTo(30792268L);
//        assertThat(client.questionsResponses().get(0).owner().displayName()).isEqualTo("newbie");
//        assertThat(client.questionsResponses().get(0).lastActivityDate().toString())
//            .isEqualTo("2023-08-08T13:40:20Z");
    }
}
