package edu.java.bot.service.commands;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackCommandTest extends CommandTestInit {
    private final String[] unsupportedLinks = {
        "blabla.com",
        "содержанки.рф",
        "github.co",
    };
    private final String[] supportedLinks = {
        "github.com",
        "github.com/shd/logic2023a",
        "stackoverflow.com",
    };

    private final String[] alreadyAddedLinks = {
        "github.com",
        "https://github.com"
    };


    @Override
    public void init() {
        super.init();

        linkRepository.registerId(chatId);

        command = new TrackCommand(updateHandlerService, linkRepository);
    }

    @Test
    public void testTrackOnEmptyLink() {
        when(super.message.text()).thenReturn("/track");
        SendMessage message = command.handle(update);

        assertThat(message.getParameters().get("text").toString())
            .isEqualTo("Invalid link. Try, for example /track github.com");
    }

    @Test
    public void testTrackOnUnsupportedLink() {
        for (String s : unsupportedLinks) {
            when(super.message.text()).thenReturn("/track " + s);

            SendMessage response = command.handle(update);

            assertThat(response.getParameters().get("text").toString())
                .isEqualTo("Cannot find webpage by the link: " + s);
        }
    }

    @Test
    public void testTrackOnSupportedLinks() {
        for (String s : supportedLinks) {
            when(super.message.text()).thenReturn("/track " + s);

            SendMessage response = command.handle(update);

            assertThat(response.getParameters().get("text").toString())
                .isEqualTo("Link is tracking now! " + System.lineSeparator() + s);
        }
    }

    @Test
    public void testTrackOnAlreadyAddedLinks() {
        when(super.message.text()).thenReturn("/track " + alreadyAddedLinks[0]);

        SendMessage successfulResponse = command.handle(update);

        assertThat(successfulResponse.getParameters().get("text").toString())
            .isEqualTo("Link is tracking now! " + System.lineSeparator() + alreadyAddedLinks[0]);

        for (String s : alreadyAddedLinks) {
            when (super.message.text()).thenReturn("/track " + s);

            SendMessage response = command.handle(update);

            assertThat(response.getParameters().get("text").toString())
                .isEqualTo("This link has been already tracking: " + System.lineSeparator() + s);
        }
    }
}
