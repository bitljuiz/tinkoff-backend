package edu.java.bot.service.updates;

import edu.java.bot.chain.Handler;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UpdateHandlerService {
    private static final String[] WEBPAGE_HATS = {
        "",
        "https://",
    };
    private final List<Handler> handlerList;

    public UpdateHandlerService(List<Handler> handlerList) {
        this.handlerList = handlerList;
    }

    public String isValid(String link) {
        String newLink;
        for (String hat : WEBPAGE_HATS) {
            newLink = hat + link;
            for (Handler handler : handlerList) {
                if (handler.linkExists(newLink) && handler.linkHasDomain(newLink)) {
                    return newLink;
                }
            }
        }
        return null;
    }
}
