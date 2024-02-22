package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private static final String START_COMMAND = "/start";
    private static final String START_COMMAND_DESCRIPTION = "is used to register an user";
    private static final String IS_ALREADY_REGISTERED = "You are already registered!";

    private static final String SUCCESSFUL_START_COMMAND_MESSAGE = "Successfully registered!";
    private final LinkRepository linkRepository;

    public StartCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

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
        if (linkRepository.registerId(update.message().chat().id())) {
            return new SendMessage(update.message().chat().id(), SUCCESSFUL_START_COMMAND_MESSAGE);
        }
        return new SendMessage(update.message().chat().id(), IS_ALREADY_REGISTERED);
    }
}
