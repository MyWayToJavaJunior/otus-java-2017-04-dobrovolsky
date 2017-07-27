package ru.otus.dobrovolsky.base.messages;

import java.util.Map;

public interface CacheService {
    void init();

    Map<String, Object> getCacheMap();

    void setCacheMap(Map<String, Object> cacheMap);
}

