package ru.otus.dobrovolsky.base;

import ru.otus.dobrovolsky.base.dataSets.DataSet;

import java.util.List;

public interface DBService {
    String getLocalStatus();

    <T extends DataSet> void save(T dataSet);

    <T extends DataSet> T read(Class<T> clazz, long id);

    <T extends DataSet> T readByName(Class<T> clazz, String name);

    <T extends DataSet> List<T> readAll(Class<T> clazz);

    void shutdown();
}