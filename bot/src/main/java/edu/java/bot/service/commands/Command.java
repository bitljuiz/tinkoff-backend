package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public interface Command {

    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String[] messageParts = update.message().text().split(" ", 2);
            return command().equalsIgnoreCase(messageParts[0]);
        }
        return false;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
