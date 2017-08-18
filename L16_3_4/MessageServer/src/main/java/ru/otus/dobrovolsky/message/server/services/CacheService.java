package ru.otus.dobrovolsky.message.server.services;

import java.util.Map;

public interface CacheService {
    Map<String, Object> getCacheMap();

    void setCacheMap(Map<String, Object> cacheMap);
}

