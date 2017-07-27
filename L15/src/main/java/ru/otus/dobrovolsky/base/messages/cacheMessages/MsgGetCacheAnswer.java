package ru.otus.dobrovolsky.base.messages.cacheMessages;

import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MsgToFrontend;
import ru.otus.dobrovolsky.messageSystem.Address;

import java.util.Map;

public class MsgGetCacheAnswer extends MsgToFrontend {
    private final Map<String, Object> cacheMap;

    MsgGetCacheAnswer(Address from, Address to, Map<String, Object> cacheMap) {
        super(from, to);
        this.cacheMap = cacheMap;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.getCacheService().setCacheMap(this.cacheMap);
    }
}
