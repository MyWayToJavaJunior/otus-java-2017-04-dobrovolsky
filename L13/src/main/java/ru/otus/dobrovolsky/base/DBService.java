package ru.otus.dobrovolsky.base;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;
import ru.otus.dobrovolsky.dbService.CacheDescriptor;

import java.util.List;
import java.util.Map;

public interface DBService {
    String getLocalStatus();

    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    AddressDataSet readAddressById(long id);

    UserDataSet readByNamedQuery(long id);

    List<UserDataSet> readAll();

    void shutdown();

    Map<String, Object> getCacheMap();

    Statistics getStatistics();

    SessionFactory getSessionFactory();

    CacheDescriptor getCacheDescriptor();
}