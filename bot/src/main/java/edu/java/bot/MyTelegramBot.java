package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.processor.UserMessageProcessor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyTelegramBot implements Bot {
    private final TelegramBot telegramBot;
    private final UserMessageProcessor msgProcessor;

    @Autowired
    public MyTelegramBot(UserMessageProcessor msgProcessor, ApplicationConfig config) {
        this.msgProcessor = msgProcessor;
        this.telegramBot = new TelegramBot(config.telegramToken());
        this.telegramBot.execute(
            new SetMyCommands(
                    this.msgProcessor.commands()
                        .stream()
                        .map(
                            command -> new BotCommand(command.command(), command.description()))
                        .toArray(BotCommand[]::new)
            )
        );
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            SendMessage response = msgProcessor.process(update);

            if (response != null) {
                execute(response);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }
}
