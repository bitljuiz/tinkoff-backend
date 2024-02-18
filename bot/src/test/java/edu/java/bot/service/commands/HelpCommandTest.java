package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.TextMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest extends CommandTestInit{
    @Override
    public void init() {
        super.init();
        Command startCommand = mock(Command.class);
        Command helpCommand = mock(Command.class);
        when(startCommand.command()).thenReturn(TextMessages.START_COMMAND);
        when(startCommand.description()).thenReturn(TextMessages.START_COMMAND_DESCRIPTION);
        when(helpCommand.command()).thenReturn(TextMessages.HELP_COMMAND);
        when(helpCommand.description()).thenReturn(TextMessages.HELP_COMMAND_DESCRIPTION);
        commands.addAll(Arrays.asList(startCommand, helpCommand));

        command = new HelpCommand(commands);

        System.out.println();
    }

    @Test
    public void testHelpCommand() {
        SendMessage response = command.handle(update);

        assertThat(response.getParameters().get("text").toString()).isEqualTo(
            TextMessages.HELP_COMMAND_MESSAGE + "* " + TextMessages.START_COMMAND + " "
                + TextMessages.START_COMMAND_DESCRIPTION + TextMessages.LINE_SEPARATOR + "* " +
                TextMessages.HELP_COMMAND + " "
                + TextMessages.HELP_COMMAND_DESCRIPTION + TextMessages.LINE_SEPARATOR
        );
    }
}
