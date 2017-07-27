package ru.otus.dobrovolsky.base.messages.cacheMessages;

import ru.otus.dobrovolsky.base.messages.CacheService;
import ru.otus.dobrovolsky.base.messages.MessageSystemContext;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.Addressee;

import java.util.Map;

public class CacheServiceImpl implements CacheService, Addressee {
    private final Address address;
    private final MessageSystemContext context;
    private Map<String, Object> cacheMap;

    public CacheServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public Map<String, Object> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(Map<String, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }
}
