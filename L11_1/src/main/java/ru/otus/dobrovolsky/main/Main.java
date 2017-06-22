package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.cache.MyCacheEngineImpl;
import ru.otus.dobrovolsky.dbService.DBService;
import ru.otus.dobrovolsky.dbService.dataSets.User;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();

        MyCacheEngineImpl<Long, User> cache = new MyCacheEngineImpl<>(0, 0, 0, true);
        dbService.registerCache(cache);

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

        System.out.println("    Checking cache hits:    " + cache.getHitCount());
        System.out.println("    Checking cache misses:    " + cache.getMissCount());

        System.out.println("    Trying to get users info from cache after first queries:");
        restoredUser = dbService.loadUser(1);
        System.out.println("    restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(2);
        System.out.println("    restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(3);
        System.out.println("    restoredUser:   " + restoredUser.toString());
        restoredUser = dbService.loadUser(4);
        System.out.println("    restoredUser:   " + restoredUser.toString());

        System.out.println("    Checking cache hits:    " + cache.getHitCount());
        System.out.println("    Checking cache misses:    " + cache.getMissCount());

        System.out.println("1   Trying to drop table before next operations");
        dbService.cleanUp();
        cache.clear();
        System.out.println("1   OK");

        System.out.println("2   Trying to create new table before next operations");
        dbService.createTable(User.class);
        System.out.println("2   OK");
        System.out.println("2   Trying to create some records in table");
        for (int i = 1; i <= 128; i++) {
            user = new User(Integer.toString(random.nextInt()), Math.abs(random.nextInt()));
            dbService.saveUser(user);
            System.out.println("    " + i + "    " + user.toString());
        }

        System.out.println("2   Selecting records by id");
        for (int i = 1; i <= 128; i++) {
            restoredUser = dbService.loadUser(i);
            System.out.println("    restoredUser:   " + restoredUser.toString());
        }

        System.out.println("    Checking cache hits:    " + cache.getHitCount());
        System.out.println("    Checking cache misses:    " + cache.getMissCount());
        System.out.println("    Cache size after first round of querying dataSets from DB:    " + cache.getCacheSize());
        System.out.println();

        System.out.println("    Trying to get users info from cache after first queries:");

        for (int i = 1; i <= 128; i++) {
            if (i % 32 == 0) {
                Thread.sleep(300);
                System.out.println("    Trying to simulate pause in user queries to DB...");
            }
            restoredUser = dbService.loadUser(i);
            System.out.println("    restoredUser:   " + restoredUser.toString());
        }

        System.out.println("    Checking cache hits:    " + cache.getHitCount());
        System.out.println("    Checking cache misses:    " + cache.getMissCount());

        dbService.cleanUp();
        dbService.closeConnection();

        cache.stopCleaningThread();
    }
}
