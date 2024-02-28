package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.chain.GithubHandler;
import edu.java.bot.chain.StackOverflowHandler;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.service.commands.Command;
import edu.java.bot.service.commands.HelpCommand;
import edu.java.bot.service.commands.ListCommand;
import edu.java.bot.service.commands.StartCommand;
import edu.java.bot.service.commands.TrackCommand;
import edu.java.bot.service.commands.UntrackCommand;
import edu.java.bot.service.updates.UpdateHandlerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMessageProcessorTest {
    private UserMessageProcessorImpl userMessageProcessor;
    private List<Command> allCommands;

    @Mock
    private Update update;

    @Mock
    private Message message;
    private final Long chatId = 371L;

    @Mock
    Chat chat;

    private final String[] invalidTextResponse = {
        "start",
        "\start",
        "help",
        "track",
        "untrack",
        "/strt",
        "/trck",
        "/ntrck",
        "/hlp",
        "/lst",
    };

    @BeforeEach
    public void init() {
        LinkRepository linkRepository = new LinkRepository();

        List<Command> commands = List.of(
            new StartCommand(linkRepository),
            new ListCommand(linkRepository),
            new TrackCommand(new UpdateHandlerService(List.of(
                    new GithubHandler(),
                    new StackOverflowHandler()
                )
            ), linkRepository),
            new UntrackCommand(linkRepository)
        );

        allCommands = new ArrayList<>(List.of(new HelpCommand(commands)));

        allCommands.addAll(commands);

        userMessageProcessor = new UserMessageProcessorImpl(
            allCommands
        );
    }
    @Test
    public void testCommandList() {
        assertEquals(allCommands, userMessageProcessor.commands());
    }

    @Test
    public void testInvalidCommands() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        for (String resp : invalidTextResponse) {
            when(message.text()).thenReturn(resp);
            SendMessage response = userMessageProcessor.process(update);

            assertThat(response.getParameters().get("text").toString())
                .isEqualTo("I don't know the command " + resp);
        }
    }

    @Test
    public void testValidCommand() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        SendMessage response;
        when(message.text()).thenReturn("/start");

        response = userMessageProcessor.process(update);

        assertEquals(response.getParameters().get("text").toString(), "Successfully registered!");

        when(message.text()).thenReturn("/help");

        response = userMessageProcessor.process(update);

        assertEquals(response.getParameters().get("text").toString(),
            """
            The list of bot commands:
            * /start is used to register an user
            * /list shows a list of currently tracking links
            * /track is used to start tracking the link
            * /untrack is used to finish tracking the link
            * /help shows a list of bot commands
            """.replace("\n", System.lineSeparator()));

        when(message.text()).thenReturn("/list");

        response = userMessageProcessor.process(update);

        assertEquals(response.getParameters().get("text").toString(),
            "You are not tracking any link now" + System.lineSeparator());
    }
}
