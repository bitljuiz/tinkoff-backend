package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final LinkRepository linkRepository;

    private final String listCommand = "/list";
    private final String listCommandDescription = "shows a list of currently tracking links";

    public final String successfulListMessage = "Links that are tracking now: " + System.lineSeparator();
    public final String invalidListMessage = "You are not tracking any link now" + System.lineSeparator();

    public static final String IS_NOT_REGISTERED = "You are not registered." + System.lineSeparator()
        + "Use /start to register.";

    public ListCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public String command() {
        return listCommand;
    }

    @Override
    public String description() {
        return listCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        HashSet<String> links;
        try {
            links = linkRepository.getData(chatId);
        } catch (Exception ignored) {
            return new SendMessage(chatId, IS_NOT_REGISTERED);
        }

        StringBuilder linksInList = new StringBuilder();
        if (!links.isEmpty()) {
            links.forEach(link -> linksInList.append(link).append(System.lineSeparator()));
            return new SendMessage(chatId, successfulListMessage + linksInList);
        }
        return new SendMessage(chatId, invalidListMessage);
    }
}
