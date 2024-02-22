package edu.java.bot.service.updates;

import edu.java.bot.chain.GithubHandler;
import edu.java.bot.chain.Handler;
import edu.java.bot.chain.StackOverflowHandler;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UpdateHandlerServiceTest {
    private final List<Handler> onlyGithubHandler = List.of(
        new GithubHandler()
    );

    private final List<Handler> twoHandlers = List.of(
        new GithubHandler(),
        new StackOverflowHandler()
    );

    private final String[] correctLinks = {
        "github.com",
        "http://github.com",
        "https://github.com",
        "stackoverflow.com",
        "https://stackoverflow.com"
    };

    private final String[] incorrectLinks = {
        "github.co",
        "http:/\\//github.com",
        "jgithb.com",
        "stackoverflow.c",
        "belbierd.ru",
    };

    @Test
    public void testOnlyWithGithubHandler() {
        UpdateHandlerService updateHandlerService = new UpdateHandlerService(onlyGithubHandler);

        int i = 0;
        while (!correctLinks[i].contains("stackoverflow.com")) {
            assertFalse(updateHandlerService.isValid(correctLinks[i++]).isEmpty());
        }
        while (i < correctLinks.length) {
            assertNull(updateHandlerService.isValid(correctLinks[i++]));
        }
    }

    @Test
    public void testOnlyWithTwoHandlers() {
        UpdateHandlerService updateHandlerService = new UpdateHandlerService(twoHandlers);

        for (String link : correctLinks) {
            assertFalse(updateHandlerService.isValid(link).isEmpty());
        }
    }

    @Test
    public void testIncorrectLinks() {
        UpdateHandlerService updateHandlerService = new UpdateHandlerService(twoHandlers);

        for (String link : incorrectLinks) {
            assertNull(updateHandlerService.isValid(link));
        }
    }
}
