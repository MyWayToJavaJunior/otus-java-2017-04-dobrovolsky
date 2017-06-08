package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.dbService.DBService;
import ru.otus.dobrovolsky.dbService.dataSets.User;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        dbService.printConnectInfo();
        Random random = new Random();

        System.out.println("1   Trying to create some records in table");
        User user = new User("Nicholas", 28);
        dbService.saveUser(user);
        System.out.println("    " + user.toString());
        user = new User("1", 1);
        dbService.saveUser(user);
        System.out.println("    " + user.toString());
        user = new User("Katherine", 27);
        dbService.saveUser(user);
        System.out.println("    " + user.toString());
        user = new User("TEST", 123);
        dbService.saveUser(user);
        System.out.println("    " + user.toString());

        System.out.println("1   Selecting records by id");
        User restoredUser = dbService.loadUser(1);
        System.out.println("    restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(2);
        System.out.println("    restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(3);
        System.out.println("    restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(4);
        System.out.println("    restoredUser:   " + restoredUser.toString());

        System.out.println("1   Trying to drop table before next operations");
        dbService.cleanUp();
        System.out.println("1   OK");

        System.out.println("2   Trying to create new table before next operations");
        dbService.createTable(User.class);
        System.out.println("2   OK");
        System.out.println("2   Trying to create some records in table");
        for (int i = 1; i <= 50; i++) {
            user = new User(Integer.toString(random.nextInt()), Math.abs(random.nextInt()));
            dbService.saveUser(user);
            System.out.println("    " + i + "    " + user.toString());
        }

        System.out.println("2   Selecting records by id");
        for (int i = 1; i <= 50; i++) {
            restoredUser = dbService.loadUser(i);
            System.out.println("    restoredUser:   " + restoredUser.toString());
        }

        dbService.cleanUp();
        dbService.closeConnection();
    }
}
