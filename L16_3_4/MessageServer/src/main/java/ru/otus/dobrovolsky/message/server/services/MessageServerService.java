package ru.otus.dobrovolsky.message.server.services;

import ru.otus.dobrovolsky.message.server.Address;
import ru.otus.dobrovolsky.message.server.Addressee;

import java.util.Map;
import java.util.logging.Logger;

public class MessageServerService implements CacheService, Addressee {
    private static final Logger LOGGER = Logger.getLogger(MessageServerService.class.getName());
    private static MessageServerService instance;
    private Map<String, Object> cacheMap;

    private MessageServerService() {
    }

    public static MessageServerService getInstance() {
        if (instance == null) {
            instance = new MessageServerService();
        }

        return instance;
    }

    public Address getAddress() {
        return new Address("MessageServerService");
    }

    public Map<String, Object> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<String, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }
}
