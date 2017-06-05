package ru.otus.dobrovolsky.test;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.dobrovolsky.users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JDBCTest {
    @Test
    public void Test() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("otus-dobrovolsky-JDBC");
        EntityManager em = entityManagerFactory.createEntityManager();

        User user = new User("Nicholas", 28);

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        User restoredUser = em.find(User.class, 1L);

        Assert.assertNotNull(restoredUser);

        em.close();
        entityManagerFactory.close();
    }
}
