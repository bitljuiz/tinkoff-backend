package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Service;
import static edu.java.bot.util.TextMessages.HELP_COMMAND;
import static edu.java.bot.util.TextMessages.HELP_COMMAND_DESCRIPTION;
import static edu.java.bot.util.TextMessages.HELP_COMMAND_MESSAGE;
import static edu.java.bot.util.TextMessages.LINE_SEPARATOR;

@Service
public class HelpCommand implements Command {
    private final StringBuilder helpCommandListMessage;

    public HelpCommand(List<Command> commands) {
        helpCommandListMessage = new StringBuilder();
        commands.forEach(command ->
            helpCommandListMessage.append("* ")
                .append(command.command())
                .append(" ")
                .append(command.description())
                .append(LINE_SEPARATOR)
        );
    }

    @Override
    public String command() {
        return HELP_COMMAND;
    }

    @Override
    public String description() {
        return HELP_COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
            update.message().chat().id(), HELP_COMMAND_MESSAGE + helpCommandListMessage
        );
    }
}
