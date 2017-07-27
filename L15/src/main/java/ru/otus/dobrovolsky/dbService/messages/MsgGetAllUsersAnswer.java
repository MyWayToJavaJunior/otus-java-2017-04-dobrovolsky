package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MsgToFrontend;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;

import java.util.List;

public class MsgGetAllUsersAnswer extends MsgToFrontend {
    private final List<UserDataSet> userDataSetList;

    public MsgGetAllUsersAnswer(Address from, Address to, List<UserDataSet> userDataSetList) {
        super(from, to);
        this.userDataSetList = userDataSetList;
    }

    @Override
    public void exec(FrontendService frontendService) {
    }
}
