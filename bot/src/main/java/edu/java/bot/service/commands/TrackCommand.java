package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.service.updates.UpdateHandlerService;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final String TRACK_COMMAND = "/track";
    private final String TRACK_COMMAND_DESCRIPTION = "is used to start tracking the link";
    private final String SUCCESSFUL_TRACK_MESSAGE = "Link is tracking now! " + System.lineSeparator();
    private final String LINK_IS_ACTUALLY_TRACKING = "This link has been already tracking: " + System.lineSeparator();
    private final String INVALID_FORMAT_TRACK_MESSAGE =
        "Invalid link. Try, for example " + TRACK_COMMAND + " github.com";
    private final String INVALID_TRACK_MESSAGE = "Cannot find webpage by the link: ";
    public static final String IS_NOT_REGISTERED = "You are not registered." + System.lineSeparator() +
        "Use /start to register.";
    private final UpdateHandlerService updateHandlerService;
    private final LinkRepository linkRepository;

    public TrackCommand(UpdateHandlerService updateHandlerService, LinkRepository linkRepository) {
        this.updateHandlerService = updateHandlerService;
        this.linkRepository = linkRepository;
    }

    @Override
    public String command() {
        return TRACK_COMMAND;
    }

    @Override
    public String description() {
        return TRACK_COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String[] messageParts = update.message().text().split("\\s+", 2);

        if (!linkRepository.containsId(chatId)) {
            return new SendMessage(chatId, IS_NOT_REGISTERED);
        }

        if (messageParts.length < 2) {
            return new SendMessage(chatId, INVALID_FORMAT_TRACK_MESSAGE);
        }

        String newLink;
        if ((newLink = updateHandlerService.isValid(messageParts[1])) == null) {
            return new SendMessage(chatId, INVALID_TRACK_MESSAGE + messageParts[1]);
        }
        if (!linkRepository.addData(chatId, newLink)) {
            return new SendMessage(chatId, LINK_IS_ACTUALLY_TRACKING + messageParts[1]);
        }

        return new SendMessage(chatId, SUCCESSFUL_TRACK_MESSAGE + messageParts[1]);
    }
}
