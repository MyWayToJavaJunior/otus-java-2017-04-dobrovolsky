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

        System.out.println("trying to insert users data to database");
        em.getTransaction().begin();
        for (User u : users) {
            em.persist(u);
        }
        em.getTransaction().commit();

        System.out.println("trying to restore users data from database");
        User restoredUser = em.find(User.class, 1L);
        System.out.println(restoredUser.toString());
        restoredUser = em.find(User.class, 2L);
        System.out.println(restoredUser.toString());
        restoredUser = em.find(User.class, 3L);
        System.out.println(restoredUser.toString());

        em.close();
        entityManagerFactory.close();
    }
}
