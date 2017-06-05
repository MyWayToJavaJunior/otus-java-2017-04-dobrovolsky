package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Nicholas", 28));
        users.add(new User("Katherine", 27));
        users.add(new User("Test", 999));

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("otus-dobrovolsky-JDBC");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        for (User u : users) {
            em.persist(u);
        }
        em.getTransaction().commit();
        em.close();
    }
}
