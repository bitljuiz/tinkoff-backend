package edu.java.bot.chain;

import org.springframework.stereotype.Component;
import static edu.java.bot.util.TextMessages.GITHUB_DOMAIN;

@Component
public class GithubHandler extends Handler {
    public GithubHandler() {
        super(GITHUB_DOMAIN);
    }
}
