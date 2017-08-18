package ru.otus.dobrovolsky.message.server;


import ru.otus.dobrovolsky.message.Msg;

import java.util.logging.Logger;

public class MsgGetCache extends Msg {
    private static final Logger LOGGER = Logger.getLogger(MsgGetCache.class.getName());

    public MsgGetCache(Address from, Address to) {
        super(MsgGetCache.class, from, to, null);
    }
}
