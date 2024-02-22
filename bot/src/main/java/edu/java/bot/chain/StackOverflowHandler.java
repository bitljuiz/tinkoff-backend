package edu.java.bot.chain;

import org.springframework.stereotype.Component;

@Component
public class StackOverflowHandler extends Handler {
    public StackOverflowHandler() {
        super("stackoverflow.com");
    }
}
