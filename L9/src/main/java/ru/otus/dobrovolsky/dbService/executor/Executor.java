package ru.otus.dobrovolsky.dbService.executor;

import ru.otus.dobrovolsky.users.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

    public void save(User user) throws SQLException {
        execUpdate("INSERT INTO users (name, age) VALUES ('" + user.getName() + "', '" + user.getAge() + "')");
    }

    public <T extends User> T load(long id, Class<T> clazz) throws SQLException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Statement stmt = connection.createStatement();
        stmt.execute("SELECT * FROM users WHERE id=" + id);
        ResultSet result = stmt.getResultSet();
        result.next();

        T restoredUser = clazz.getConstructor(Long.class, String.class, Integer.class).newInstance(result.getLong(1), result.getString(2), result.getInt(3));
        result.close();
        stmt.close();

        return restoredUser;
    }

    public void createTable() throws SQLException {
        execUpdate("CREATE TABLE IF NOT EXISTS users (id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), age INT(3) NOT NULL)");
    }

    public void dropTable() throws SQLException {
        execUpdate("DROP TABLE IF EXISTS users");
    }
}
