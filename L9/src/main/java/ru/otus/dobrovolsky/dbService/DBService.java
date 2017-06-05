package ru.otus.dobrovolsky.dbService;

import ru.otus.dobrovolsky.dbService.executor.Executor;
import ru.otus.dobrovolsky.users.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final Connection connection;
    private final Executor executor;

    public DBService() throws SQLException {
        connection = getConnection();
        executor = new Executor(connection);
        try {
            cleanUp();
            createUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws DBException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //default port
                    "db_example?" +              //db name
                    "useSSL=false&" +            //do not use ssl
                    "user=test&" +              //default login, allowed value - tully
                    "password=test";            //default password, allowed value - tully

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        try {
            executor.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name:        " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version:     " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver:         " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit:     " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() throws SQLException {
        System.out.println("Trying to create users table...");
        try {
            executor.createTable();
            System.out.println("Users table was created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void saveUser(User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            executor.save(user);
            System.out.println("Saved user:     " + user.getName() + "  " + user.getAge());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loadUser(long id, Class<?> clazz) throws DBException {
        try {
            connection.setAutoCommit(false);
            return executor.load(id, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
