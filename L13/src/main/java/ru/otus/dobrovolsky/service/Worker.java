package ru.otus.dobrovolsky.service;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.dataSets.AddressDataSet;
import ru.otus.dobrovolsky.base.dataSets.PhoneDataSet;
import ru.otus.dobrovolsky.base.dataSets.UserDataSet;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {
    //    private static final Logger logger = LoggerFactory.getLogger(Worker.class);
    private final DBService dbService;

    Worker(DBService dbService) {
        this.dbService = dbService;
        run();
    }

//    public static Logger getLogger() {
//        return logger;
//    }

    public void run() {
//        String status = dbService.getLocalStatus();
//        logger.info("Status:    " + status);

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

//        UserDataSet dataSet = dbService.read(1);
        dbService.read(1);
//        logger.info(dataSet.toString());

//        dataSet = dbService.readByName("Katherine");
        dbService.readByName("Katherine");
//        logger.info(dataSet.toString());

//        logger.info("======================readAll==============================");
//        List<UserDataSet> dataSets = dbService.readAll();
        dbService.readAll();
//        for (UserDataSet userDataSet : dataSets) {
//            logger.info(userDataSet.toString());
//        }

//        logger.info("======================User activity simulation==============================");

        dbService.save(new UserDataSet("test1", new AddressDataSet("1", 1111), new PhoneDataSet(0, "12345")));
        dbService.save(new UserDataSet("test2", new AddressDataSet("2", 2222), new PhoneDataSet(1, "12346")));
        dbService.save(new UserDataSet("test3", new AddressDataSet("3", 3333), new PhoneDataSet(2, "12347")));
        dbService.save(new UserDataSet("test4", new AddressDataSet("4", 4444), new PhoneDataSet(3, "12348")));
        dbService.save(new UserDataSet("test5", new AddressDataSet("5", 5555), new PhoneDataSet(4, "12349")));

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

//            logger.info(dbService.read(1).toString());
//            logger.info(dbService.read(2).toString());
//            logger.info(dbService.read(3).toString());
//            logger.info(dbService.read(4).toString());
//            logger.info(dbService.read(5).toString());
//            logger.info(dbService.read(6).toString());
//            logger.info(dbService.read(7).toString());
//            logger.info(dbService.read(8).toString());
//            logger.info(dbService.read(2).toString());
//            logger.info(dbService.read(1).toString());
//
//            logger.info(dbService.read(1).toString());
//            logger.info(dbService.read(2).toString());
//            logger.info(dbService.read(3).toString());
//            logger.info(dbService.read(4).toString());
//            logger.info(dbService.read(5).toString());
//            logger.info(dbService.read(6).toString());
//            logger.info(dbService.read(7).toString());
//            logger.info(dbService.read(8).toString());
//            logger.info(dbService.read(2).toString());
//            logger.info(dbService.read(1).toString());
//
//            logger.info(dbService.readAddressById(1).toString());
//            logger.info(dbService.readAddressById(2).toString());
//            logger.info(dbService.readAddressById(3).toString());
//            logger.info(dbService.readAddressById(4).toString());
//            logger.info(dbService.readAddressById(5).toString());
//
//            logger.info(dbService.readByName("Test").toString());

//            List<UserDataSet> dataSets1 = dbService.readAll();
            dbService.readAll();
//            for (UserDataSet userDataSet : dataSets1) {
//                logger.info(userDataSet.toString());
//            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}