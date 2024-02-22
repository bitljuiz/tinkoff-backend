package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private StringBuilder helpCommandListMessage;
    private final String helpCommand = "/help";
    private final String helpCommandDescription = "shows a list of bot commands";
    private final String helpCommandMessage = "The list of bot commands:" + System.lineSeparator();

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
        return helpCommand;
    }

    @Override
    public String description() {
        return helpCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
            update.message().chat().id(), helpCommandMessage + helpCommandListMessage
        );
    }
}
