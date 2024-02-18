package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import static edu.java.bot.util.TextMessages.START_COMMAND;
import static edu.java.bot.util.TextMessages.START_COMMAND_DESCRIPTION;
import static edu.java.bot.util.TextMessages.START_COMMAND_MESSAGE;

@Service
public class StartCommand implements Command {
    @Override
    public String command() {
        return START_COMMAND;
    }

    @Override
    public String description() {
        return START_COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), START_COMMAND_MESSAGE);
    }
}
