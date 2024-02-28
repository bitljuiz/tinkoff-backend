package edu.java.bot.service.commands;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UntrackCommandTest extends CommandTestInit {

    private final String[] invalidResponses = {
        "github.co",
        "git.com",
        "stackoverflow.comm",
    };
    private final String[] validResponses = {
        "github.com",
        "https://stackoverflow.com",
    };

    @Override
    public void init() {
        super.init();

        linkRepository.registerId(chatId);

        linkRepository.addData(chatId, "https://github.com");
        linkRepository.addData(chatId, "https://stackoverflow.com");

        command = new UntrackCommand(linkRepository);
    }

    @Test
    public void testEmptyUntrack() {
        when(super.message.text()).thenReturn("/untrack");

        SendMessage response = command.handle(update);

        assertThat(response.getParameters().get("text").toString())
            .isEqualTo("Invalid link. You can try /untrack github.com");
    }

    @Test
    public void testInvalidResponse() {
        for (String response : invalidResponses) {
            when(super.message.text()).thenReturn("/untrack " + response);

            SendMessage responseMessage = command.handle(update);

            assertThat(responseMessage.getParameters().get("text").toString())
                .isEqualTo("This link hadn't been tracking " + response);
        }
    }

    @Test
    public void testValidResponse() {
       for (String response : validResponses) {
           when(super.message.text()).thenReturn("/untrack " + response);

           SendMessage responseMessage = command.handle(update);

           assertThat(responseMessage.getParameters().get("text").toString())
               .isEqualTo("Finish tracking the link " + response);
       }
    }
}
