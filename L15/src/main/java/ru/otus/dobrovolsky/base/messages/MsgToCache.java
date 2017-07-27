package ru.otus.dobrovolsky.base.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.Addressee;
import ru.otus.dobrovolsky.messageSystem.Message;

import java.time.LocalDateTime;

public abstract class MsgToCache extends Message {
    private final Logger LOGGER = LoggerFactory.getLogger(MsgToCache.class);

    public MsgToCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CacheService) {
            LOGGER.info(LocalDateTime.now() + ":    Executing request for CacheService addressee");
            exec((CacheService) addressee);
        } else {
            LOGGER.error(LocalDateTime.now() + ":    Wrong addressee! This addressee works only with CacheService " +
                    "requests!");
        }
    }

    public abstract void exec(CacheService cacheService);
}
