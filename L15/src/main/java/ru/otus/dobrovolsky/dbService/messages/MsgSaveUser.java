package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.MsgToDB;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

public class MsgSaveUser extends MsgToDB {
    private final MessageSystem messageSystem;
    private final UserDataSet userDataSet;

    public MsgSaveUser(MessageSystem messageSystem, Address from, Address to, UserDataSet userDataSet) {
        super(from, to);
        this.userDataSet = userDataSet;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.save(userDataSet);
        messageSystem.sendMessage(new MsgSaveUserAnswer(getTo(), getFrom(), userDataSet));
    }
}
