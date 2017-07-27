package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.MsgToDB;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

import java.util.List;

public class MsgGetAllUsers extends MsgToDB {
    private final MessageSystem messageSystem;

    public MsgGetAllUsers(MessageSystem messageSystem, Address from, Address to) {
        super(from, to);
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        List<UserDataSet> userDataSetList = dbService.readAll();
        messageSystem.sendMessage(new MsgGetAllUsersAnswer(getTo(), getFrom(), userDataSetList));
    }
}
