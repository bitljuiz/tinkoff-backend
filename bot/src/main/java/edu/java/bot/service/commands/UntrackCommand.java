package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private final String UNTRACK_COMMAND = "/untrack";
    private final String UNTRACK_COMMAND_DESCRIPTION = "is used to finish tracking the link";
    private final String IS_NOT_REGISTERED = "You are not registered." + System.lineSeparator() +
        "Use /start to register.";
    private final String INVALID_FORMAT_UNTRACK_MESSAGE =
        "Invalid link. You can try " + UNTRACK_COMMAND + " github.com";
    private final String INVALID_UNTRACK_MESSAGE = "This link hadn't been tracking ";
    private final String SUCCESSFUL_UNTRACK_MESSAGE = "Finish tracking the link ";
    private final LinkRepository linkRepository;

    public UntrackCommand(LinkRepository linkRepository) { this.linkRepository = linkRepository; }

    @Override
    public String command() {
        return UNTRACK_COMMAND;
    }

    @Override
    public String description() {
        return UNTRACK_COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        if (!linkRepository.containsId(chatId)) {
            return new SendMessage(chatId, IS_NOT_REGISTERED);
        }

        String[] messageParts = update.message().text().split("\\s+", 2);

        if (messageParts.length < 2) {
            return new SendMessage(chatId, INVALID_FORMAT_UNTRACK_MESSAGE);
        }

        if (linkRepository.removeData(chatId, messageParts[1])) {
            return new SendMessage(chatId, SUCCESSFUL_UNTRACK_MESSAGE + messageParts[1]);
        }
        return new SendMessage(chatId, INVALID_UNTRACK_MESSAGE + messageParts[1]);
    }
}
