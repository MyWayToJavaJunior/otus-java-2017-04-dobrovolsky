package ru.otus.dobrovolsky.base;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import ru.otus.dobrovolsky.base.messages.CacheDescriptor;
import ru.otus.dobrovolsky.base.messages.MessageSystemContext;
import ru.otus.dobrovolsky.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.dataSet.UserDataSet;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
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

    void init();

    MessageSystemContext getContext();
}