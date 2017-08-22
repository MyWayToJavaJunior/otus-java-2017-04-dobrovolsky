package ru.otus.dobrovolsky.backend.server;

import ru.otus.dobrovolsky.backend.cache.CacheDescriptor;
import ru.otus.dobrovolsky.backend.service.DBServiceHibernateImpl;
import ru.otus.dobrovolsky.backend.worker.Worker;
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
    private static final int DELAY = 500;
    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;
    private Address address;
    private int num;
    private volatile boolean isRegistered = false;

    public DBServer(int num) {
        this.num = num;
        this.address = new Address("DBServer" + this.num);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() throws Exception {
        CacheDescriptor cacheDescriptor = new CacheDescriptor(DBServiceHibernateImpl.getInstance());

        SocketClientChannel client = new SocketClientChannel(new Socket(HOST, SOCKET_PORT));
        client.init();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            try {
                while (!isRegistered) {
                    LOGGER.info("Sending registration message:  not registered yet");
                    client.send(new MsgRegistration(address, new Address("MsgServerService")));
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });

        executorService.submit(() -> {
            try {
                while (true) {
                    Msg receivedMsg = client.take();
                    LOGGER.info("RECEIVED MESSAGE:  " + receivedMsg.getClass() + "   from:   " + receivedMsg.getFrom() + " to:   " + receivedMsg.getTo());
                    if ((!isRegistered) && (receivedMsg.getClass().getName().equals(MsgRegistrationAnswer.class.getName()))) {
                        LOGGER.info("Receiving registration message answer:  not registered yet");
                        LOGGER.info("Registered on MsgServer successfully");
                        isRegistered = true;
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
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });

        Worker worker = new Worker();

        executorService.submit(worker::run);
    }
}
