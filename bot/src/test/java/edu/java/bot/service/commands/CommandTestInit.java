package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.chain.GithubHandler;
import edu.java.bot.chain.Handler;
import edu.java.bot.chain.StackOverflowHandler;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.service.updates.UpdateHandlerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public abstract class CommandTestInit {
    @Mock
    protected Update update;
    @Mock
    protected Message message;
    @Mock
    protected Chat chat;

    protected Command command;

    protected LinkRepository linkRepository;

    protected UpdateHandlerService updateHandlerService;

    protected final Long chatId = 371L;

    protected final List<Command> commands = new ArrayList<>();

    @BeforeEach
    public void init() {
        linkRepository = new LinkRepository();
        List<Handler> handlerList = List.of(
            new GithubHandler(),
            new StackOverflowHandler()
        );

        updateHandlerService = new UpdateHandlerService(handlerList);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
    }
}
