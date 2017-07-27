package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.MsgToDB;
import ru.otus.dobrovolsky.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.MessageSystem;

public class MsgGetAddress extends MsgToDB {
    private final MessageSystem messageSystem;
    private final long id;

    public MsgGetAddress(MessageSystem messageSystem, Address from, Address to, long id) {
        super(from, to);
        this.id = id;
        this.messageSystem = messageSystem;
    }

    @Override
    public void exec(DBService dbService) {
        AddressDataSet addressDataSet = dbService.readAddressById(id);
        messageSystem.sendMessage(new MsgGetAddressAnswer(getTo(), getFrom(), addressDataSet));
    }
}
