package ru.otus.dobrovolsky.main;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.PhoneDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;
import ru.otus.dobrovolsky.dbService.DBServiceHibernateImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();

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

        UserDataSet dataSet = dbService.read(UserDataSet.class, 1);
        System.out.println(dataSet);

        dataSet = dbService.readByName(UserDataSet.class, "Katherine");
        System.out.println(dataSet);

        System.out.println("======================readAll==============================");
        List<UserDataSet> dataSets = dbService.readAll(UserDataSet.class);
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();
    }
}