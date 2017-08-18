package ru.otus.dobrovolsky.message.server;

import ru.otus.dobrovolsky.message.Msg;

import java.util.Map;
import java.util.logging.Logger;

public class MsgGetCacheAnswer extends Msg {
    private static final Logger LOGGER = Logger.getLogger(MsgGetCache.class.getName());

    public MsgGetCacheAnswer(Address from, Address to, Map<String, Object> cacheMap) {
        super(MsgGetCacheAnswer.class, from, to, cacheMap);
    }
}
