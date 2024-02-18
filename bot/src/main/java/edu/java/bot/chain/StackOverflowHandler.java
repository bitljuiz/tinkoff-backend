package edu.java.bot.chain;

import org.springframework.stereotype.Component;
import static edu.java.bot.util.TextMessages.STACKOVERFLOW_DOMAIN;

@Component
public class StackOverflowHandler extends Handler {
    public StackOverflowHandler() {
        super(STACKOVERFLOW_DOMAIN);
    }
}
