package ru.otus.dobrovolsky.message.server;


import ru.otus.dobrovolsky.message.Msg;

import java.util.logging.Logger;

public class MsgUpdateCacheAnswer extends Msg {
    private static final Logger LOGGER = Logger.getLogger(MsgUpdateCacheAnswer.class.getName());

    public MsgUpdateCacheAnswer(Address from, Address to) {
        super(MsgUpdateCacheAnswer.class, from, to, null);
    }
}
