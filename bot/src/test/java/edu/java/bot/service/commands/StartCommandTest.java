package edu.java.bot.service.commands;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest extends CommandTestInit {

    @Override
    public void init() {
        super.init();
        command = new StartCommand(linkRepository);
    }

    @Test
    public void testStartMessage() {
        SendMessage response = command.handle(update);
        assertThat(response.getParameters().get("text")).isEqualTo("Successfully registered!");
        assertThat(response.getParameters().get("chat_id")).isEqualTo(chatId);
    }
}
