package edu.java.bot.chain;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import static edu.java.bot.util.TextMessages.UPDATE_MESSAGE;

public abstract class Handler {
    private final String domainName;

    // To delete
    private final Logger log;

    public Handler(String domainName) {
        this.domainName = domainName;
        log = Logger.getLogger(Handler.class.getName());
    }

    public boolean linkExists(String link) {
        try {
           URI.create(link).toURL();
           return true;
        } catch (IllegalArgumentException | MalformedURLException ignored) {
        }
        return false;
    }

    public boolean linkHasDomain(String link) {
        return link.contains(domainName);
    }

    public boolean hasUpdate(String link) {
        return true;
    }

    public void getUpdate(String link) {
        log.info(UPDATE_MESSAGE);
    }

    public String domainName() {
        return domainName;
    }
}
