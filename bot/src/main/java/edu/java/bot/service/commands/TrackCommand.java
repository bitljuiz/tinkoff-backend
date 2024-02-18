package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.updates.UpdateHandlerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import static edu.java.bot.util.TextMessages.INVALID_FORMAT_TRACK_MESSAGE;
import static edu.java.bot.util.TextMessages.INVALID_TRACK_MESSAGE;
import static edu.java.bot.util.TextMessages.SUCCESSFUL_TRACK_MESSAGE;
import static edu.java.bot.util.TextMessages.TRACK_COMMAND;
import static edu.java.bot.util.TextMessages.TRACK_COMMAND_DESCRIPTION;

@Service
public class TrackCommand implements Command {
    private final UpdateHandlerService updateHandlerService;

    public TrackCommand(UpdateHandlerService updateHandlerService) {
        this.updateHandlerService = updateHandlerService;
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
        String[] message_parts = update.message().text().split("\\s+", 2);

        if (message_parts.length < 2) {
            return new SendMessage(chatId, INVALID_FORMAT_TRACK_MESSAGE);
        }

        if (updateHandlerService.dataIsValid(chatId, message_parts[1])) {
            return new SendMessage(chatId, SUCCESSFUL_TRACK_MESSAGE + message_parts[1]);
        }
        return new SendMessage(chatId, INVALID_TRACK_MESSAGE + message_parts[1]);
    }
}
