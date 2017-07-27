package ru.otus.dobrovolsky.base.messages.cacheMessages;

import ru.otus.dobrovolsky.base.messages.CacheDescriptor;
import ru.otus.dobrovolsky.base.messages.CacheService;
import ru.otus.dobrovolsky.base.messages.MsgToCache;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

public class MsgCacheUpdate extends MsgToCache {
    private final MessageSystem messageSystem;
    private final CacheDescriptor cacheDescriptor;

    public MsgCacheUpdate(MessageSystem messageSystem, Address from, Address to, CacheDescriptor cacheDescriptor) {
        super(from, to);
        this.cacheDescriptor = cacheDescriptor;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(CacheService cacheService) {
        cacheDescriptor.updateFields();
        messageSystem.sendMessage(new MsgCacheUpdateAnswer(getTo(), getFrom()));
    }
}
