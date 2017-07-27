package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MsgToFrontend;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;

public class MsgGetUserAnswer extends MsgToFrontend {
    private final UserDataSet userDataSet;

    public MsgGetUserAnswer(Address from, Address to, UserDataSet userDataSet) {
        super(from, to);
        this.userDataSet = userDataSet;
    }

    @Override
    public void exec(FrontendService frontendService) {
    }
}
