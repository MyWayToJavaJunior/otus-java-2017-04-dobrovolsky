package ru.otus.dobrovolsky.dbService.executor;

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

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }

    @Deprecated
    public void createTable() throws SQLException {
        execUpdate("CREATE TABLE IF NOT EXISTS users (id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), age INT(3) NOT NULL)");
    }

    @Deprecated
    public void dropTable() throws SQLException {
        execUpdate("DROP TABLE IF EXISTS users");
    }
}
