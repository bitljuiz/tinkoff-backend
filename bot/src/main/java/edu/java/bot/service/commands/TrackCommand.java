package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.service.updates.UpdateHandlerService;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final String trackCommand = "/track";
    private final String trackCommandDescription = "is used to start tracking the link";
    private final String successfulTrackMessage = "Link is tracking now! " + System.lineSeparator();
    private final String linkIsActuallyTracking = "This link has been already tracking: " + System.lineSeparator();
    private final String invalidFormatTrackMessage =
        "Invalid link. Try, for example " + trackCommand + " github.com";
    private final String invalidTrackMessage = "Cannot find webpage by the link: ";
    public static final String IS_NOT_REGISTERED = "You are not registered." + System.lineSeparator()
        + "Use /start to register.";
    private final UpdateHandlerService updateHandlerService;
    private final LinkRepository linkRepository;

    public TrackCommand(UpdateHandlerService updateHandlerService, LinkRepository linkRepository) {
        this.updateHandlerService = updateHandlerService;
        this.linkRepository = linkRepository;
    }

    @Override
    public String command() {
        return trackCommand;
    }

    @Override
    public String description() {
        return trackCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String[] messageParts = update.message().text().split("\\s+", 2);

        if (!linkRepository.containsId(chatId)) {
            return new SendMessage(chatId, IS_NOT_REGISTERED);
        }

        String newLink = updateHandlerService.isValid(messageParts[1]);
        if (newLink == null) {
            return new SendMessage(chatId, invalidTrackMessage + messageParts[1]);
        }
        if (!linkRepository.addData(chatId, newLink)) {
            return new SendMessage(chatId, linkIsActuallyTracking + messageParts[1]);
        }

        return new SendMessage(chatId, successfulTrackMessage + messageParts[1]);
    }
}
