package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.commands.Command;
import java.util.List;
import org.springframework.stereotype.Component;
import static edu.java.bot.util.TextMessages.INVALID_COMMAND_MESSAGE;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commands;

    public UserMessageProcessorImpl(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        return new SendMessage(update.message().chat().id(), INVALID_COMMAND_MESSAGE);
    }
}
