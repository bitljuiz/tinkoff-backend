package edu.java.bot.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LinkRepository {
    private final String[] webpageHats = {
        "",
        "https://",
    };
    private final Map<Long, HashSet<String>> links;

    public LinkRepository() {
        links = new HashMap<>();
    }

    public boolean addData(Long chatId, String link) {
        return links.get(chatId).add(link);
    }

    public boolean registerId(Long chatId) {
        if (links.containsKey(chatId)) {
            return false;
        }
        links.computeIfAbsent(chatId, val -> new HashSet<>());
        return true;
    }

    public boolean removeData(Long chatId, String link) {
        boolean wasDeleted = false;
        if (links.containsKey(chatId)) {
            for (String hat : webpageHats) {
                if (!wasDeleted) {
                    String newLink = hat + link;
                    wasDeleted = links.get(chatId).remove(newLink);
                }
            }
        }
        return wasDeleted;
    }

    public boolean containsId(Long chatId) {
        return links.containsKey(chatId);
    }

    public HashSet<String> getData(Long chatId) throws Exception {
        if (!containsId(chatId)) {
            throw new Exception();
        }
        return links.get(chatId);
    }
}
