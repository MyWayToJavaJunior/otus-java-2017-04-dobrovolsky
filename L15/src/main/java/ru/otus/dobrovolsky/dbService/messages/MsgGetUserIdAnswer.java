package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MsgToFrontend;
import ru.otus.dobrovolsky.messageSystem.Address;

public class MsgGetUserIdAnswer extends MsgToFrontend {
    private final String name;
    private final long id;

    public MsgGetUserIdAnswer(Address from, Address to, String name, long id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
    }
}
