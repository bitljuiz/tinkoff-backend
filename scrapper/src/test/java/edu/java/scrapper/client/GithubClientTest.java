package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.scrapper.ScrapperApplication;
import edu.java.scrapper.service.GithubClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScrapperApplication.class)
public class GithubClientTest {
    @Autowired
    GithubClientService githubClient;
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
    void testFetchGithubRepository() {
        wireMockServer.stubFor(
            get("bitljuiz/tinkoff-backend")
                .willReturn(ok()
                    .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(
                        """
                            {
                                "owner": {
                                    "id": 117773229
                                    "login": "bitljuiz",
                                },
                                "updated_at": "2024-02-26T00:00Z",
                                "pushed_at": "2024-02-25T00:00Z"
                            }
                            """
                    )
                )
        );
        var client = githubClient.fetchGithubUserResponse("bitljuiz", "tinkoff-backend").block();

        assertThat(client).isNotNull();
        assertThat(client.owner().id()).isEqualTo(117773229L);
        assertThat(client.owner().login()).isEqualTo("bitljuiz");
        assertThat(client.pushTime().toString()).isEqualTo("2024-02-25T20:36:21Z");
        assertThat(client.updateTime().toString()).isEqualTo("2024-02-05T15:03:32Z");
    }
}
