package edu.java.bot.chain;

import org.springframework.stereotype.Component;

@Component
public class GithubHandler extends Handler {
    public GithubHandler() {
        super("github.com");
    }
}
