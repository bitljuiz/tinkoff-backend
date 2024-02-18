package edu.java.bot.service.updates;

import edu.java.bot.chain.Handler;
import edu.java.bot.repository.LinkRepository;
import edu.java.bot.repository.RepositoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UpdateHandlerService {
    private final List<Handler> handlerList;
    private final LinkRepository repository;

    @Autowired
    public UpdateHandlerService(List<Handler> handlerList, LinkRepository repository) {
        this.handlerList = handlerList;
        this.repository = repository;
    }

    public boolean dataIsValid(Long chatId, String link) {
        if (isValid(link)) {
            return repository.addData(new RepositoryData(chatId, link));
        }
        return false;
    }

    private boolean isValid(String link) {
        for (Handler handler : handlerList) {
            if (handler.linkExists(link) && handler.linkHasDomain(link)) {
                return true;
            }
        }
        return false;
    }

    private void getUpdate(String link) {
        for (Handler handler : handlerList) {
            if (handler.hasUpdate(link)) {
                handler.getUpdate(link);
            }
        }
    }
}
