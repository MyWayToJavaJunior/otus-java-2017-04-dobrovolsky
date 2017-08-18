package ru.otus.dobrovolsky.backend;

import ru.otus.dobrovolsky.backend.cache.CacheDescriptor;
import ru.otus.dobrovolsky.message.Msg;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;
import ru.otus.dobrovolsky.message.server.*;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServer {
    private static final Logger LOGGER = Logger.getLogger(DBServer.class.getName());
    private static final int PAUSE_MS = 2500;
    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;
    private Address address;

    public DBServer() {
        address = new Address("DBServer");
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        CacheDescriptor cacheDescriptor = new CacheDescriptor(DBServiceHibernateImpl.getInstance());

        SocketClientChannel client = new SocketClientChannel(new Socket(HOST, SOCKET_PORT));
        client.init();
        client.send(new MsgRegistration(address, new Address("MessageServerService")));

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                while (true) {
                    Msg receivedMsg = client.take();
                    LOGGER.info("RECEIVED MESSAGE:  " + receivedMsg.getClass() + "   from:   " + receivedMsg.getFrom() + " to:   " + receivedMsg.getTo());
                    if (receivedMsg.getClass().getName().equals(MsgRegistrationAnswer.class.getName())) {
                        LOGGER.info("Registered on MessageServer successfully");
                    }
                    if (receivedMsg.getClass().getName().equals(MsgUpdateCache.class.getName())) {
                        cacheDescriptor.updateFields();
                        Msg msg = new MsgUpdateCacheAnswer(receivedMsg.getTo(), receivedMsg.getFrom());
                        client.send(msg);
                    }
                    if (receivedMsg.getClass().getName().equals(MsgGetCache.class.getName())) {
                        LOGGER.info("RECEIVED Cache message");
                        Map<String, Object> cacheMap = cacheDescriptor.getCacheMap();
                        MsgGetCacheAnswer msg = new MsgGetCacheAnswer(receivedMsg.getTo(), receivedMsg.getFrom(), cacheMap);
                        client.send(msg);
                    }
                    Thread.sleep(PAUSE_MS);
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });

        Worker worker = new Worker();

        executorService.submit(worker::run);
    }
}