package ru.otus.dobrovolsky.base.messages.cacheMessages;

import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MsgToFrontend;
import ru.otus.dobrovolsky.messageSystem.Address;

public class MsgCacheUpdateAnswer extends MsgToFrontend {

    MsgCacheUpdateAnswer(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(FrontendService frontendService) {
    }
}
