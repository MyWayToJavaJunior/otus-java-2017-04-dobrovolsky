package ru.otus.dobrovolsky.base;

import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;

import java.util.List;

public interface DBService {
    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    AddressDataSet readAddressById(long id);

    UserDataSet readByNamedQuery(long id);

    List<UserDataSet> readAll();

    void shutdown();

    void getQueryCacheHitCount();

    void getSecondLevelCacheMissCount();

    void getQueryCacheMissCount();

    void getSecondLevelCacheHitCount();

    void getQueryStatistics();

    void getQueries();

    void getSecondLevel();
}