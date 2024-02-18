package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.LinkRepository;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import static edu.java.bot.util.TextMessages.INVALID_LIST_MESSAGE;
import static edu.java.bot.util.TextMessages.LINE_SEPARATOR;
import static edu.java.bot.util.TextMessages.LIST_COMMAND;
import static edu.java.bot.util.TextMessages.LIST_COMMAND_DESCRIPTION;
import static edu.java.bot.util.TextMessages.SUCCESSFUL_LIST_MESSAGE;

@Service
public class ListCommand implements Command {
    private final LinkRepository repository;

    public ListCommand(LinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public String command() {
        return LIST_COMMAND;
    }

    @Override
    public String description() {
        return LIST_COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        ArrayList<String> links;

        if (!(links = repository.getData(chatId)).isEmpty()) {
            StringBuilder linksInList = new StringBuilder();

            links.forEach(link -> linksInList.append(link).append(LINE_SEPARATOR));
            return new SendMessage(chatId, SUCCESSFUL_LIST_MESSAGE + linksInList);
        }
        return new SendMessage(chatId, INVALID_LIST_MESSAGE);
    }
}
