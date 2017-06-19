package ru.otus.dobrovolsky.dbService.dao;


import ru.otus.dobrovolsky.dbService.dataSets.DataSet;
import ru.otus.dobrovolsky.dbService.executor.Executor;
import ru.otus.dobrovolsky.reflect.PackageMetaData;

import javax.persistence.Column;
import javax.persistence.PersistenceException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import static ru.otus.dobrovolsky.reflect.ReflectionHelper.*;

public class UsersDAO {
    private Executor executor;
    private PackageMetaData pmd;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
        this.pmd = PackageMetaData.getInstance();
    }

    public <T extends DataSet> T loadById(long id, Class<T> clazz) throws IllegalAccessException,
            InstantiationException, SQLException, InvocationTargetException, NoSuchMethodException {
        T object = clazz.newInstance();
        object.setId(id);
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = pmd.getTableName(clazz);
        String queryString = "SELECT * FROM " + tableName + " WHERE id=" + id;
        return executor.execQuery(queryString, result -> {
            result.next();
            return makeObject(object, result);
        });
    }

    public <T extends DataSet> void saveUser(T dataSet) throws SQLException, NoSuchFieldException,
            InstantiationException, IllegalAccessException {
        Class clazz = dataSet.getClass();
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = pmd.getTableName(clazz);
        String queryString = "INSERT INTO " + tableName + " " + getStringForInsert(dataSet, Column.class) +
                " VALUES " + getStringForInsert(dataSet, Field.class);
        executor.execUpdate(queryString);
    }

    public <T extends DataSet> void createTable(Class<T> clazz) throws SQLException, IllegalAccessException {
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = pmd.getTableName(clazz);
        String queryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(id bigint(20) NOT NULL AUTO_INCREMENT " +
                "PRIMARY KEY, " + pmd.getColumnsString(clazz);
        executor.execUpdate(queryString);
    }

    public <T extends DataSet> void dropTable(Class<T> clazz) throws SQLException {
        String tableName = pmd.getTableName(clazz);
        executor.execUpdate("DROP TABLE IF EXISTS " + tableName);
    }
}
