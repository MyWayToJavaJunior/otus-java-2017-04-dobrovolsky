package ru.otus.dobrovolsky.dbService;

import ru.otus.dobrovolsky.dbService.executor.Executor;
import ru.otus.dobrovolsky.users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public class DBService {
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final Executor executor;

    public DBService() throws SQLException {
        entityManagerFactory = Persistence.createEntityManagerFactory("otus-dobrovolsky-JDBC");
        entityManager = entityManagerFactory.createEntityManager();
        executor = new Executor(entityManager);
    }

    public void printConnectInfo() {
        Connection connection = entityManager.unwrap(Connection.class);
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();

            System.out.println("DB name:        " + metaData.getDatabaseProductName());
            System.out.println("DB version:     " + metaData.getDatabaseProductVersion());
            System.out.println("Driver:         " + metaData.getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws DBException {
        entityManager.close();
    }

    public void saveUser(User user) throws DBException {
        Executor executor = new Executor(entityManager);
        try {
            executor.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(List<User> users) throws DBException {
        Executor executor = new Executor(entityManager);
        try {
            executor.save(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loadUser(long id, Class<?> clazz) throws DBException {
        Executor executor = new Executor(entityManager);
        try {
            return executor.load(id, clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
