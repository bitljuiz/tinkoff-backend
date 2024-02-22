package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private final String untrackCommand = "/untrack";
    private final String untrackCommandDescription = "is used to finish tracking the link";
    private final String isNotRegistered = "You are not registered." + System.lineSeparator()
        + "Use /start to register.";
    private final String invalidFormatUntrackMessage =
        "Invalid link. You can try " + untrackCommand + " github.com";
    private final String invalidUntrackMessage = "This link hadn't been tracking ";
    private final String successfulUntrackMessage = "Finish tracking the link ";
    private final LinkRepository linkRepository;

    public UntrackCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public String command() {
        return untrackCommand;
    }

    @Override
    public String description() {
        return untrackCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        if (!linkRepository.containsId(chatId)) {
            return new SendMessage(chatId, isNotRegistered);
        }

        String[] messageParts = update.message().text().split("\\s+", 2);

        if (messageParts.length < 2) {
            return new SendMessage(chatId, invalidFormatUntrackMessage);
        }

        if (linkRepository.removeData(chatId, messageParts[1])) {
            return new SendMessage(chatId, successfulUntrackMessage + messageParts[1]);
        }
        return new SendMessage(chatId, invalidUntrackMessage + messageParts[1]);
    }
}
