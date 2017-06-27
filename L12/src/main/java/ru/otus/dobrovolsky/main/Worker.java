package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.PhoneDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {
    private final DBService dbService;

    public Worker(DBService dbService) {
        this.dbService = dbService;
    }

    public void run() {
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        dbService.save(new UserDataSet("Nicholas", new AddressDataSet("Lenina 1", 460018), new PhoneDataSet(1234,
                "111111"), new PhoneDataSet(2345, "222222")));

        dbService.save(new UserDataSet("Katherine", new AddressDataSet("Lenina 2", 460018), new PhoneDataSet
                (3456, "333333"), new PhoneDataSet(4567, "444444")));

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet(5678, "1234567890"));
        phones.add(new PhoneDataSet(6789, "0987654321"));
        AddressDataSet address = new AddressDataSet("Lenina 3", 460000);
        UserDataSet testUser = new UserDataSet("Test", address, phones);

        dbService.save(testUser);

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.readByName("Katherine");
        System.out.println(dataSet);

        System.out.println("======================readAll==============================");
        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        System.out.println("======================User activity simulation==============================");

        dbService.save(new UserDataSet("test1", new AddressDataSet("1", 1111), new PhoneDataSet(00, "12345")));
        dbService.save(new UserDataSet("test2", new AddressDataSet("2", 2222), new PhoneDataSet(01, "12346")));
        dbService.save(new UserDataSet("test3", new AddressDataSet("3", 3333), new PhoneDataSet(02, "12347")));
        dbService.save(new UserDataSet("test4", new AddressDataSet("4", 4444), new PhoneDataSet(03, "12348")));
        dbService.save(new UserDataSet("test5", new AddressDataSet("5", 5555), new PhoneDataSet(04, "12349")));

        while (true) {
            System.out.println(dbService.read(1));
            System.out.println(dbService.read(2));
            System.out.println(dbService.read(3));
            System.out.println(dbService.read(4));
            System.out.println(dbService.read(5));
            System.out.println(dbService.read(6));
            System.out.println(dbService.read(7));
            System.out.println(dbService.read(2));
            System.out.println(dbService.read(1));

            System.out.println(dbService.readByNamedQuery(1));
            System.out.println(dbService.readByNamedQuery(2));
            System.out.println(dbService.readByNamedQuery(3));
            System.out.println(dbService.readByNamedQuery(4));
            System.out.println(dbService.readByNamedQuery(5));
            System.out.println(dbService.readByNamedQuery(6));
            System.out.println(dbService.readByNamedQuery(7));
            System.out.println(dbService.readByNamedQuery(2));
            System.out.println(dbService.readByNamedQuery(1));

            System.out.println(dbService.readAddressById(1));
            System.out.println(dbService.readAddressById(2));
            System.out.println(dbService.readAddressById(3));
            System.out.println(dbService.readAddressById(4));
            System.out.println(dbService.readAddressById(5));

            System.out.println(dbService.readByName("Test"));

            List<UserDataSet> dataSets1 = dbService.readAll();
            for (UserDataSet userDataSet : dataSets1) {
                System.out.println(userDataSet);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}