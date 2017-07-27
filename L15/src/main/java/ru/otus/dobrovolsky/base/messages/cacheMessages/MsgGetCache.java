package ru.otus.dobrovolsky.base.messages.cacheMessages;

import ru.otus.dobrovolsky.base.messages.CacheDescriptor;
import ru.otus.dobrovolsky.base.messages.CacheService;
import ru.otus.dobrovolsky.base.messages.MsgToCache;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

import java.util.Map;

public class MsgGetCache extends MsgToCache {
    private final MessageSystem messageSystem;
    private final CacheDescriptor cacheDescriptor;

    public MsgGetCache(MessageSystem messageSystem, Address from, Address to, CacheDescriptor cacheDescriptor) {
        super(from, to);
        this.cacheDescriptor = cacheDescriptor;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(CacheService cacheService) {
        Map<String, Object> cacheMap = cacheDescriptor.getCacheMap();
        messageSystem.sendMessage(new MsgGetCacheAnswer(getTo(), getFrom(), cacheMap));
    }
}
