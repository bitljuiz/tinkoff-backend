package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.repository.RepositoryData;
import org.springframework.stereotype.Service;
import static edu.java.bot.util.TextMessages.INVALID_FORMAT_UNTRACK_MESSAGE;
import static edu.java.bot.util.TextMessages.INVALID_UNTRACK_MESSAGE;
import static edu.java.bot.util.TextMessages.SUCCESSFUL_UNTRACK_MESSAGE;
import static edu.java.bot.util.TextMessages.UNTRACK_COMMAND;
import static edu.java.bot.util.TextMessages.UNTRACK_COMMAND_DESCRIPTION;

@Service
public class UntrackCommand implements Command {
    private final LinkRepository repository;

    public UntrackCommand(LinkRepository repository) {
        this.repository = repository;
    }

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
        String[] messageParts = update.message().text().split("\\s+", 2);

        if (messageParts.length < 2) {
            return new SendMessage(chatId, INVALID_FORMAT_UNTRACK_MESSAGE);
        }

        if (repository.removeData(new RepositoryData(chatId, messageParts[1]))) {
            return new SendMessage(chatId, SUCCESSFUL_UNTRACK_MESSAGE + messageParts[1]);
        }
        return new SendMessage(chatId, INVALID_UNTRACK_MESSAGE + messageParts[1]);
    }
}
