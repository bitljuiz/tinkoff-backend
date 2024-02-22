package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;
@Component
public class HelpCommand implements Command {
    private StringBuilder helpCommandListMessage;
    private final String HELP_COMMAND = "/help";
    private final String HELP_COMMAND_DESCRIPTION = "shows a list of bot commands";

    private final String HELP_COMMAND_MESSAGE = "The list of bot commands:" + System.lineSeparator();
    private void buildCommandList(List<Command> commands) {
        helpCommandListMessage = new StringBuilder();
        commands.forEach(command ->
            helpCommandListMessage
                .append("* ")
                .append(command.command())
                .append(" ")
                .append(command.description())
                .append(System.lineSeparator())
        );
        helpCommandListMessage
            .append("* ").append(command()).append(" ").append(description()).append(System.lineSeparator());
    }

    public HelpCommand(List<Command> commands) {
        buildCommandList(commands);
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
