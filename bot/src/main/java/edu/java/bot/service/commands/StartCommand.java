package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private final String startCommand = "/start";
    private final String startCommandDescription = "is used to register an user";
    private final String isAlreadyRegistered = "You are already registered!";

    private final String successfulStartCommandMessage = "Successfully registered!";
    private final LinkRepository linkRepository;

    public StartCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public String command() {
        return startCommand;
    }

    @Override
    public String description() {
        return startCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        SendMessage response;
        if (linkRepository.registerId(update.message().chat().id())) {
            response =  new SendMessage(update.message().chat().id(), successfulStartCommandMessage);
        } else {
            response =  new SendMessage(update.message().chat().id(), isAlreadyRegistered);
        }
        return response;
    }
}
