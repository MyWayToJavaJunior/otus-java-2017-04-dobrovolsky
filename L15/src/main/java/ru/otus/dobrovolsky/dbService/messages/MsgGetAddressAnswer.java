package ru.otus.dobrovolsky.dbService.messages;

import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MsgToFrontend;
import ru.otus.dobrovolsky.dataSet.AddressDataSet;
import ru.otus.dobrovolsky.messageSystem.Address;

public class MsgGetAddressAnswer extends MsgToFrontend {
    private final AddressDataSet addressDataSet;

    MsgGetAddressAnswer(Address from, Address to, AddressDataSet addressDataSet) {
        super(from, to);
        this.addressDataSet = addressDataSet;
    }

    @Override
    public void exec(FrontendService frontendService) {
    }
}
