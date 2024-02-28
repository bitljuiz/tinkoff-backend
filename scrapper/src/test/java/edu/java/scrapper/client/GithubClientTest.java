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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScrapperApplication.class)
public class GithubClientTest {
    @Autowired
    GithubClientService githubClient;
    private WireMockServer wireMockServer;

    private final OffsetDateTime updateTime = OffsetDateTime.of(
        2017, 11, 30, 0, 0, 0, 0, ZoneOffset.UTC
    );

    private final OffsetDateTime pushTime = OffsetDateTime.of(
        2016, 11, 30, 0, 0, 0, 0, ZoneOffset.UTC
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
                                "pushed_at": "%s"
                                "updated_at": "%s"
                            }
                            """.formatted(pushTime.toString(), updateTime.toString())
                    )
                )
        );

        System.out.printf("%s, %s\n", updateTime.toString(), pushTime.toString());
        githubClient.fetchGithubUserResponse("bitljuiz", "tinkoff-backend").subscribe(
            client -> {
                assertThat(client.owner().id()).isEqualTo(117773229L);
                assertThat(client.owner().login()).isEqualTo("bitljuiz");
                assertThat(client.pushTime().toString())
                    .isEqualTo(pushTime.toString());
                assertThat(client.updateTime().toString())
                    .isEqualTo(updateTime.toString());
            }
        );
    }
}
