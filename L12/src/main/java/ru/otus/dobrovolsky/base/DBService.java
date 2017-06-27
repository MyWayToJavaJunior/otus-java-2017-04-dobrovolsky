package ru.otus.dobrovolsky.base;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
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

    long getQueryCacheHitCount();

    long getSecondLevelCacheMissCount();

    long getQueryCacheMissCount();

    long getSecondLevelCacheHitCount();

    long getSecondLevelHitU();

    long getSecondLevelMissU();

    long getSecondLevelHitP();

    long getSecondLevelMissP();

    long getSecondLevelHitA();

    long getSecondLevelMissA();

    long getQueryCachePutCount();

    long getSecondLevelCachePutCount();

    long getSessionOpenCount();

    long getSessionCloseCount();

    String getSecondLevelCacheRegionNames();

    long getSecondLevelPutCountU();

    long getSecondLevelPutCountP();

    long getSecondLevelPutCountA();

    long getSecondLevelSizeU();

    long getSecondLevelSizeP();

    long getSecondLevelSizeA();

    String getQueries();

    Statistics getStatistics();

    SessionFactory getSessionFactory();
}