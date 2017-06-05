package ru.otus.dobrovolsky.dbService.executor;

import ru.otus.dobrovolsky.users.User;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class Executor {
    private final EntityManager entityManager;

    public Executor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User user) throws SQLException {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void save(List<User> users) throws SQLException {
        entityManager.getTransaction().begin();
        for (User u : users) {
            entityManager.persist(u);
        }
        entityManager.getTransaction().commit();
    }

    public User load(long id, Class<?> clazz) throws SQLException {
        return (User) entityManager.find(clazz, id);
    }
}
