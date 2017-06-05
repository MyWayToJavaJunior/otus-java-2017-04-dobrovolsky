package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.dbService.DBService;
import ru.otus.dobrovolsky.users.User;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Nicholas", 28));
        users.add(new User("Katherine", 27));
        users.add(new User("Test", 999));

        System.out.println("trying to insert users data to database");
        DBService dbService = new DBService();
        dbService.saveUser(users);

        System.out.println("trying to select users from database");
        User restoredUser = dbService.loadUser(1L, User.class);
        System.out.println(restoredUser.toString());
        restoredUser = dbService.loadUser(2L, User.class);
        System.out.println(restoredUser.toString());
        restoredUser = dbService.loadUser(3L, User.class);
        System.out.println(restoredUser.toString());

        System.out.println("trying to insert per user to database");
        dbService.saveUser(new User("Otus", 100));
        dbService.saveUser(new User("Java", 200));
        System.out.println("trying to select per user from database");
        restoredUser = dbService.loadUser(4L, User.class);
        System.out.println(restoredUser.toString());
        restoredUser = dbService.loadUser(5L, User.class);
        System.out.println(restoredUser.toString());

        dbService.closeConnection();
    }
}
