package ru.otus.dobrovolsky.base.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.Addressee;
import ru.otus.dobrovolsky.messageSystem.Message;

import java.time.LocalDateTime;

public abstract class MsgToFrontend extends Message {
    private final Logger LOGGER = LoggerFactory.getLogger(MsgToFrontend.class);

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            LOGGER.info(LocalDateTime.now() + ":    Executing request for FrontendService addressee");
            exec((FrontendService) addressee);
        } else {
            LOGGER.error(LocalDateTime.now() + ":    Wrong addressee! This addressee works only with FrontendService " +
                    "requests!");
        }
    }

    public abstract void exec(FrontendService frontendService);
}