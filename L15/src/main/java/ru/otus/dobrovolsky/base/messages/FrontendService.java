package ru.otus.dobrovolsky.base.messages;

import ru.otus.dobrovolsky.dataSet.UserDataSet;

import java.util.Map;

public interface FrontendService {
    void init();

    void handleRequestReadUserByName(String login);

    void handleRequestSaveUser(UserDataSet userDataSet);

    void handleRequestReadUser(long id);

    void handleRequestReadAllUsers();

    void handleRequestReadUserByNamedQuery(long id);

    void handleRequestReadAddress(long id);

    Map<String, Object> handleRequestCacheStats();

    void handleRequestCacheUpdate();

    CacheService getCacheService();
}

