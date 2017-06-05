package ru.otus.dobrovolsky.dbService.executor;

import ru.otus.dobrovolsky.dbService.DBException;
import ru.otus.dobrovolsky.users.User;

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

    public User load(long id, Class<?> clazz) throws SQLException {
        if (clazz.getName() == "ru.otus.dobrovolsky.users.User") {
            Statement stmt = connection.createStatement();
            stmt.execute("SELECT * FROM users WHERE id=" + id);
            ResultSet result = stmt.getResultSet();
            result.next();
            User restoredUser = new User(result.getLong(1), result.getString(2), result.getInt(3));
            result.close();
            stmt.close();

            return restoredUser;
        } else {
            throw new DBException("This class is not supported. Can handle only ru.otus.dobrovolsky.users.User class.");
        }
    }

    public void createTable() throws SQLException {
        execUpdate("CREATE TABLE IF NOT EXISTS users (id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), age INT(3) NOT NULL)");
    }

    public void dropTable() throws SQLException {
        execUpdate("DROP TABLE IF EXISTS users");
    }
}
