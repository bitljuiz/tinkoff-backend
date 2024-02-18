package edu.java.bot.repository;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LinkRepository {
    private final Map<Long, ArrayList<String>> links;

    public LinkRepository() {
        links = new HashMap<>();
    }

    public boolean addData(RepositoryData data) {
        return links.computeIfAbsent(data.chatId(), val -> new ArrayList<>()).add(data.link());
    }

    public boolean removeData(RepositoryData data) {
        if (links.containsKey(data.chatId())) {
            return links.get(data.chatId()).remove(data.link());
        }
        return false;
    }

    public ArrayList<String> getData(Long chatId) {
        if (!links.containsKey(chatId)) {
            return new ArrayList<>();
        }
        return links.get(chatId);
    }
}
