package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.dbService.DBService;
import ru.otus.dobrovolsky.users.User;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        dbService.printConnectInfo();

        System.out.println("Trying to create some users");
        User user = new User("Nicholas", 28);
        dbService.saveUser(user);
        dbService.saveUser(new User("Katherine", 27));
        System.out.println("selecting users by id");
        User restoredUser = dbService.loadUser(1, User.class);
        System.out.println("restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(2, User.class);
        System.out.println("restoredUser:   " + restoredUser.toString());
        dbService.cleanUp();
        dbService.closeConnection();
    }
}
