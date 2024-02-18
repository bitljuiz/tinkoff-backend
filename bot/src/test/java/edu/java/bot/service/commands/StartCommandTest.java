package edu.java.bot.service.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.TextMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest extends CommandTestInit {

    @Override
    public void init() {
        super.init();
        command = new StartCommand();
    }

    @Test
    public void testStartMessage() {
        SendMessage response = command.handle(update);
        assertThat(response.getParameters().get("text")).isEqualTo(TextMessages.START_COMMAND_MESSAGE);
        assertThat(response.getParameters().get("chat_id")).isEqualTo(chatId);
    }
}
