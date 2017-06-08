package ru.otus.dobrovolsky.dbService.dao;


import ru.otus.dobrovolsky.dbService.dataSets.DataSet;
import ru.otus.dobrovolsky.dbService.dataSets.User;
import ru.otus.dobrovolsky.dbService.executor.Executor;

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
            return makeCustomObject(object, fields, result);
        });
    }

    public <T extends DataSet> void saveUser(T dataSet) throws SQLException, NoSuchFieldException,
            InstantiationException, IllegalAccessException {
        Class clazz = dataSet.getClass();
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = getTableName(clazz);
        String queryString = "INSERT INTO " + tableName + " " + getColumnsNamesString(dataSet) +
                " VALUES " + getFieldsNamesString(dataSet);
        executor.execUpdate(queryString);
    }

    public <T extends DataSet> void createTable(Class<T> clazz) throws SQLException, IllegalAccessException {
        if (!isEntity(clazz)) {
            throw new PersistenceException("Not an entity.");
        }
        String tableName = getTableName(clazz);
        String queryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(id bigint(20) NOT NULL AUTO_INCREMENT " +
                "PRIMARY KEY, " + getCreateTableString(clazz);
        executor.execUpdate(queryString);
    }

    public <T extends DataSet> void dropTable(Class<T> clazz) throws SQLException {
        executor.execUpdate("DROP TABLE IF EXISTS " + getTableName(clazz));
    }

    @Deprecated
    public void createTableOld() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS users (id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), age INT(3) NOT NULL)");
    }

    @Deprecated
    public void saveUserOld(User user) throws SQLException {
        executor.execUpdate("INSERT INTO users (name, age) VALUES ('" + user.getName() + "', '" + user.getAge() + "')");
    }

    @Deprecated
    public User loadTmp(long id) throws SQLException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        return executor.execQuery("SELECT * FROM users WHERE id=" + id, result -> {
            result.next();
            return new User(result.getString(2), result.getInt(3));
        });
    }

    @Deprecated
    public void dropTableOld() throws SQLException {
        executor.execUpdate("DROP TABLE IF EXISTS users");
    }
}
