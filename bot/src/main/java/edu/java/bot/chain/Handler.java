package edu.java.bot.chain;

import java.net.MalformedURLException;
import java.net.URI;

public abstract class Handler {
    private final String domainName;

    public Handler(String domainName) {
        this.domainName = domainName;
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
}
