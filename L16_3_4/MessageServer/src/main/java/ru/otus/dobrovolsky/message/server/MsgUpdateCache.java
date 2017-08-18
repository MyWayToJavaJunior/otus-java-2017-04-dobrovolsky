package ru.otus.dobrovolsky.message.server;


import ru.otus.dobrovolsky.message.Msg;

import java.util.logging.Logger;

public class MsgUpdateCache extends Msg {
    private static final Logger LOGGER = Logger.getLogger(MsgUpdateCache.class.getName());

    public MsgUpdateCache(Address from, Address to) {
        super(MsgUpdateCache.class, from, to, null);
    }
}
