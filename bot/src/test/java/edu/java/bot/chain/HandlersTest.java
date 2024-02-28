package edu.java.bot.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HandlersTest {
    private GithubHandler githubHandler;
    private StackOverflowHandler stackOverflowHandler;

    private final String[] validGithubLinks = {
        "https://github.com",
        "http://github.com",
        "https://github.com/shd/logic2023a",
        "http://github.com/shd/logic2023a",
        "https:/github.com/shd/logic2023a"
    };

    private final String[] invalidGithubLinks = {
        "gitwho.com",
        "github.ru",
        "gitgitgit",
        "kryakryakrya.com"
    };
    private final String[] validStackoverflowLinks = {
        "https://stackoverflow.com/questions/21805999/how-to-append-a-list-to-another-list-in-java",
        "https://stackoverflow.com/questions/27605714/test-two-instances-of-object-are-equal-junit",
        "https://stackoverflow.com/questions/15797446/how-do-i-add-values-to-a-set-inside-a-map"
    };

    private final String[] invalidStackoverflowLinks = {
        "stackoverflow.co",
        "stackstackstack.ru",
        "aaaaa.github.com"
    };

    @BeforeEach
    public void init() {
        githubHandler = new GithubHandler();
        stackOverflowHandler = new StackOverflowHandler();
    }

    @Test
    public void testGithubHandler() {
        for (String link : validGithubLinks) {
            assertTrue(githubHandler.linkHasDomain(link));
        }
        for (String link : invalidGithubLinks) {
            assertFalse(githubHandler.linkHasDomain(link));
        }
    }

    @Test
    public void testStackoverflowHandler() {
        for (String link : validStackoverflowLinks) {
            assertTrue(stackOverflowHandler.linkExists(link));
        }

        for (String link : invalidStackoverflowLinks) {
            assertFalse(stackOverflowHandler.linkExists(link));
        }
    }
}
