package edu.java.bot.repository;

public class RepositoryData {
    private Long chatId;
    private String link;

    public RepositoryData(Long chatId, String link) {
        this.chatId = chatId;
        this.link = link;
    }

    public Long chatId() {
        return chatId;
    }

    public String link() {
        return link;
    }
}
