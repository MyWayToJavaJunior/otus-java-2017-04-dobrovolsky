package ru.otus.dobrovolsky.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.dataSet.PhoneDataSet;
import ru.otus.dobrovolsky.dataSet.UserDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Worker {

    private final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private FrontendService frontendService;

    private DBService dbService;

    public Worker(DBService dbService, FrontendService frontendService) {
        this.dbService = dbService;
        this.frontendService = frontendService;
    }

    public void run() {
        dbService.getContext().getMessageSystem().start();

        frontendService.handleRequestSaveUser(new UserDataSet("Nicholas", new AddressDataSet("Lenina 1", 460018), new
                PhoneDataSet(1234,
                "111111"), new PhoneDataSet(2345, "222222")));

        frontendService.handleRequestSaveUser(new UserDataSet("Katherine", new AddressDataSet("Lenina 2", 460018), new PhoneDataSet
                (3456, "333333"), new PhoneDataSet(4567, "444444")));

        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet(5678, "1234567890"));
        phones.add(new PhoneDataSet(6789, "0987654321"));
        AddressDataSet address = new AddressDataSet("Lenina 3", 460000);
        UserDataSet testUser = new UserDataSet("Test", address, phones);

        frontendService.handleRequestSaveUser(testUser);

        frontendService.handleRequestReadUser(1);

        frontendService.handleRequestReadUserByName("Katherine");

        frontendService.handleRequestReadAllUsers();

        frontendService.handleRequestSaveUser(new UserDataSet("test1", new AddressDataSet("1", 1111), new PhoneDataSet(0, "12345")));
        frontendService.handleRequestSaveUser(new UserDataSet("test2", new AddressDataSet("2", 2222), new PhoneDataSet(1, "12346")));
        frontendService.handleRequestSaveUser(new UserDataSet("test3", new AddressDataSet("3", 3333), new PhoneDataSet(2, "12347")));
        frontendService.handleRequestSaveUser(new UserDataSet("test4", new AddressDataSet("4", 4444), new PhoneDataSet(3, "12348")));
        frontendService.handleRequestSaveUser(new UserDataSet("test5", new AddressDataSet("5", 5555), new PhoneDataSet(4, "12349")));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                frontendService.handleRequestReadUser(1);
                frontendService.handleRequestReadUser(2);
                frontendService.handleRequestReadUser(3);
                frontendService.handleRequestReadUser(4);
                frontendService.handleRequestReadUser(5);
                frontendService.handleRequestReadUser(6);
                frontendService.handleRequestReadUser(7);
                frontendService.handleRequestReadUser(8);
                frontendService.handleRequestReadUser(2);
                frontendService.handleRequestReadUser(1);

                frontendService.handleRequestReadUserByNamedQuery(2);

                frontendService.handleRequestReadUser(1);
                frontendService.handleRequestReadUser(2);
                frontendService.handleRequestReadUser(3);
                frontendService.handleRequestReadUser(4);
                frontendService.handleRequestReadUser(5);
                frontendService.handleRequestReadUser(6);
                frontendService.handleRequestReadUser(7);
                frontendService.handleRequestReadUser(8);
                frontendService.handleRequestReadUser(2);
                frontendService.handleRequestReadUser(1);

                frontendService.handleRequestReadUserByNamedQuery(2);

                frontendService.handleRequestReadAddress(1);
                frontendService.handleRequestReadAddress(2);
                frontendService.handleRequestReadAddress(3);
                frontendService.handleRequestReadAddress(4);
                frontendService.handleRequestReadAddress(5);

                frontendService.handleRequestReadUserByName("Test");
            }
        }, 1000, 1000);
    }
}