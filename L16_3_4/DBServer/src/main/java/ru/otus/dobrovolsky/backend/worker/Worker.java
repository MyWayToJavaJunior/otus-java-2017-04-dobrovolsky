package ru.otus.dobrovolsky.backend.worker;

import ru.otus.dobrovolsky.backend.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.backend.dataSet.PhoneDataSet;
import ru.otus.dobrovolsky.backend.dataSet.UserDataSet;
import ru.otus.dobrovolsky.backend.service.DBService;
import ru.otus.dobrovolsky.backend.service.DBServiceHibernateImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Worker {
    private static final Logger LOGGER = Logger.getLogger(Worker.class.getName());
    private static final DBService dbService = DBServiceHibernateImpl.getInstance();

    private void init() {
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

        dbService.read(1);

        dbService.readByName("Katherine");

        dbService.readAll();

        dbService.save(new UserDataSet("test1", new AddressDataSet("1", 1111), new PhoneDataSet(0, "12345")));
        dbService.save(new UserDataSet("test2", new AddressDataSet("2", 2222), new PhoneDataSet(1, "12346")));
        dbService.save(new UserDataSet("test3", new AddressDataSet("3", 3333), new PhoneDataSet(2, "12347")));
        dbService.save(new UserDataSet("test4", new AddressDataSet("4", 4444), new PhoneDataSet(3, "12348")));
        dbService.save(new UserDataSet("test5", new AddressDataSet("5", 5555), new PhoneDataSet(4, "12349")));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void startRequestingInfo() {
        while (true) {
            dbService.read(1);
            dbService.read(2);
            dbService.read(3);
            dbService.read(4);
            dbService.read(5);
            dbService.read(6);
            dbService.read(7);
            dbService.read(8);
            dbService.read(2);
            dbService.read(1);

            dbService.read(1);
            dbService.read(2);
            dbService.read(3);
            dbService.read(4);
            dbService.read(5);
            dbService.read(6);
            dbService.read(7);
            dbService.read(8);
            dbService.read(2);
            dbService.read(1);

            dbService.readAddressById(1);
            dbService.readAddressById(2);
            dbService.readAddressById(3);
            dbService.readAddressById(4);
            dbService.readAddressById(5);

            dbService.readByName("Test");

            dbService.readAll();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::startRequestingInfo);
    }
}