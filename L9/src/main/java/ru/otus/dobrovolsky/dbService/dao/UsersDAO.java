package ru.otus.dobrovolsky.dbService.dao;


import ru.otus.dobrovolsky.dbService.dataSets.DataSet;
import ru.otus.dobrovolsky.dbService.executor.Executor;

import javax.persistence.Column;
import javax.persistence.PersistenceException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import static ru.otus.dobrovolsky.reflect.ReflectionHelper.*;

public class UsersDAO {
    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public <T extends DataSet> T loadById(long id, Class<T> clazz) throws IllegalAccessException,
            InstantiationException, SQLException, InvocationTargetException, NoSuchMethodException {
        T object = clazz.newInstance();
        object.setId(id);
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = getTableName(clazz);
        String queryString = "SELECT * FROM " + tableName + " WHERE id=" + id;
        Field[] fields = clazz.getDeclaredFields();
        return executor.execQuery(queryString, result -> {
            result.next();
            return makeObject(object, fields, result);
        });
    }

    public <T extends DataSet> void saveUser(T dataSet) throws SQLException, NoSuchFieldException,
            InstantiationException, IllegalAccessException {
        Class clazz = dataSet.getClass();
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = getTableName(clazz);
        String queryString = "INSERT INTO " + tableName + " " + getStringForInsert(dataSet, Column.class) +
                " VALUES " + getStringForInsert(dataSet, Field.class);
        executor.execUpdate(queryString);
    }

    public <T extends DataSet> void createTable(Class<T> clazz) throws SQLException, IllegalAccessException {
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = getTableName(clazz);
        String queryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(id bigint(20) NOT NULL AUTO_INCREMENT " +
                "PRIMARY KEY, " + getColumnsAndValuesString(clazz);
        executor.execUpdate(queryString);
    }

    public <T extends DataSet> void dropTable(Class<T> clazz) throws SQLException {
        executor.execUpdate("DROP TABLE IF EXISTS " + getTableName(clazz));
    }
}
