package edu.java.bot.service.commands;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest extends CommandTestInit{
    @Override
    public void init() {
        super.init();
        Command startCommand = new StartCommand(linkRepository);
        Command listCommand = new ListCommand(linkRepository);
        Command trackCommand = new TrackCommand(updateHandlerService, new LinkRepository());
        Command untrackCommand = new UntrackCommand(linkRepository);

        commands.addAll(Arrays.asList(startCommand, listCommand, trackCommand, untrackCommand));

        command = new HelpCommand(commands);
    }

    @Test
    public void testHelpCommand() {
        SendMessage response = command.handle(update);

        String expected = """
            The list of bot commands:
            * /start is used to register an user
            * /list shows a list of currently tracking links
            * /track is used to start tracking the link
            * /untrack is used to finish tracking the link
            * /help shows a list of bot commands
            """.replace("\n", System.lineSeparator());

        assertThat(response.getParameters().get("text").toString()).isEqualTo(expected);
    }
}
