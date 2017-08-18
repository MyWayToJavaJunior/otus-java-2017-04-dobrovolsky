package ru.otus.dobrovolsky.message.server;

import ru.otus.dobrovolsky.message.Msg;

import java.util.logging.Logger;

public class MsgRegistration extends Msg {
    private static final Logger LOGGER = Logger.getLogger(MsgRegistration.class.getName());

    public MsgRegistration(Address from, Address to) {
        super(MsgRegistration.class, from, to, null);
    }
}
