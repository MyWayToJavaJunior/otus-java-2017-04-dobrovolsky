package ru.otus.dobrovolsky.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.dbService.DBService;
import ru.otus.dobrovolsky.users.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCTest {
    @Test
    public void aTest() {
        DBService dbService = null;
        try {
            dbService = new DBService();

            System.out.println("trying to insert users to database");
            dbService.saveUser(new User("Nicholas", 28));

            System.out.println("trying to select users from database");
            User restoredUser = dbService.loadUser(1l, User.class);
            System.out.println(restoredUser.toString());

            Assert.assertNotNull(restoredUser);

            dbService.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bTest() {
        DBService dbService = null;
        try {
            dbService = new DBService();
            List<User> users = new ArrayList<>();
            users.add(new User("Nicholas", 28));
            users.add(new User("Katherine", 27));
            users.add(new User("Test", 999));

            System.out.println("trying to insert users to database");
            dbService.saveUser(users);

            System.out.println("trying to select users from database");
            User restoredUser1 = dbService.loadUser(1l, User.class);
            System.out.println(restoredUser1.toString());
            User restoredUser2 = dbService.loadUser(2l, User.class);
            System.out.println(restoredUser1.toString());
            User restoredUser3 = dbService.loadUser(3l, User.class);
            System.out.println(restoredUser1.toString());

            Assert.assertNotNull(restoredUser1);
            Assert.assertNotNull(restoredUser2);
            Assert.assertNotNull(restoredUser3);

            dbService.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
