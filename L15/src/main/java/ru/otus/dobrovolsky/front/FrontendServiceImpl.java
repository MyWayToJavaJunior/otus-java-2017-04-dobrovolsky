package ru.otus.dobrovolsky.front;

import ru.otus.dobrovolsky.base.DBService;
import ru.otus.dobrovolsky.base.messages.CacheDescriptor;
import ru.otus.dobrovolsky.base.messages.CacheService;
import ru.otus.dobrovolsky.base.messages.FrontendService;
import ru.otus.dobrovolsky.base.messages.MessageSystemContext;
import ru.otus.dobrovolsky.base.messages.cacheMessages.MsgCacheUpdate;
import ru.otus.dobrovolsky.base.messages.cacheMessages.MsgGetCache;
import ru.otus.dobrovolsky.dataSet.UserDataSet;
import ru.otus.dobrovolsky.dbService.messages.*;
import ru.otus.dobrovolsky.messageSystem.Address;
import ru.otus.dobrovolsky.messageSystem.Addressee;
import ru.otus.dobrovolsky.messageSystem.Message;

import java.util.Map;

public class FrontendServiceImpl implements FrontendService, Addressee {
    private final Address address;
    private final MessageSystemContext context;
    private final CacheDescriptor cacheDescriptor;
    private final CacheService cacheService;


    public FrontendServiceImpl(MessageSystemContext context, Address address, DBService dbService, CacheService cacheService) {
        this.context = context;
        this.address = address;
        this.cacheDescriptor = dbService.getCacheDescriptor();
        this.cacheService = cacheService;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleRequestReadUser(long id) {
        Message message = new MsgGetUser(context.getMessageSystem(), getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequestReadUserByName(String login) {
        Message message = new MsgGetUserId(context.getMessageSystem(), getAddress(), context.getDbAddress(), login);
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequestReadAllUsers() {
        Message message = new MsgGetAllUsers(context.getMessageSystem(), getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequestReadUserByNamedQuery(long id) {
        Message message = new MsgGetUserByNamedQuery(context.getMessageSystem(), getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequestReadAddress(long id) {
        Message message = new MsgGetAddress(context.getMessageSystem(), getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    public void handleRequestSaveUser(UserDataSet userDataSet) {
        Message message = new MsgSaveUser(context.getMessageSystem(), getAddress(), context.getDbAddress(), userDataSet);
        context.getMessageSystem().sendMessage(message);
    }

    public Map<String, Object> handleRequestCacheStats() {
        Message message = new MsgGetCache(context.getMessageSystem(), getAddress(), context.getCacheAddress(),
                cacheDescriptor);
        context.getMessageSystem().sendMessage(message);

        return cacheService.getCacheMap();
    }

    public void handleRequestCacheUpdate() {
        Message message = new MsgCacheUpdate(context.getMessageSystem(), getAddress(), context.getCacheAddress(),
                cacheDescriptor);
        context.getMessageSystem().sendMessage(message);
    }
}
