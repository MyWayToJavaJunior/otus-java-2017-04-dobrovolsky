package ru.otus.dobrovolsky.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import ru.otus.dobrovolsky.dbService.DBException;
import ru.otus.dobrovolsky.dbService.DBService;
import ru.otus.dobrovolsky.users.User;

import java.sql.SQLException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCTest {
    @Test
    public void aTest() throws DBException {
        DBService dbService = null;
        User user = new User("Nicholas", 28);
        User restoredUser = null;
        try {
            dbService = new DBService();
            dbService.saveUser(user);
            restoredUser = dbService.loadUser(1, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull("Saved and restored user object is not null.", restoredUser);
        dbService.cleanUp();
        dbService.closeConnection();
    }

    @Test
    public void bTest() throws DBException {
        DBService dbService = null;
        User userA = new User("Nicholas", 28);
        User userB = new User("Katherine", 27);
        User restoredUserA = null;
        User restoredUserB = null;
        try {
            dbService = new DBService();
            dbService.saveUser(userA);
            dbService.saveUser(userB);
            restoredUserA = dbService.loadUser(1, User.class);
            restoredUserB = dbService.loadUser(2, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull("Saved and restored user object is not null.", restoredUserA);
        Assert.assertNotNull("Saved and restored user object is not null.", restoredUserB);
        dbService.cleanUp();
        dbService.closeConnection();
    }
}
