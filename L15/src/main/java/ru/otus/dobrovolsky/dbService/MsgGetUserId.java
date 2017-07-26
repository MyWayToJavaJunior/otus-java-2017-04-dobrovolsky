package ru.otus.dobrovolsky.dbService;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.MsgToDB;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

public class MsgGetUserId extends MsgToDB {
    private final MessageSystem messageSystem;
    private final String login;

    public MsgGetUserId(MessageSystem messageSystem, Address from, Address to, String login) {
        super(from, to);
        this.login = login;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        long id = dbService.readByName(login).getId();
        messageSystem.sendMessage(new MsgGetUserIdAnswer(getTo(), getFrom(), login, id));
    }
}
