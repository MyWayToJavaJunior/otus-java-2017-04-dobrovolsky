package ru.otus.dobrovolsky.message.server;

import ru.otus.dobrovolsky.message.Msg;

import java.util.logging.Logger;

public class MsgRegistrationAnswer extends Msg {
    private static final Logger LOGGER = Logger.getLogger(MsgRegistrationAnswer.class.getName());

    public MsgRegistrationAnswer(Address from, Address to) {
        super(MsgRegistrationAnswer.class, from, to, null);
    }
}
