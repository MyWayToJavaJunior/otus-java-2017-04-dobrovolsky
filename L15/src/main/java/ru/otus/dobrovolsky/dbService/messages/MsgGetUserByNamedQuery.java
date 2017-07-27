package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.MsgToDB;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

public class MsgGetUserByNamedQuery extends MsgToDB {
    private final MessageSystem messageSystem;
    private final long id;

    public MsgGetUserByNamedQuery(MessageSystem messageSystem, Address from, Address to, long id) {
        super(from, to);
        this.id = id;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        UserDataSet userDataSet = dbService.readByNamedQuery(id);
        messageSystem.sendMessage(new MsgGetUserAnswer(getTo(), getFrom(), userDataSet));
    }
}
