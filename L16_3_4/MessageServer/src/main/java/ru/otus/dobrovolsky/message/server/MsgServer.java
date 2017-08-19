package ru.otus.dobrovolsky.message.server;

import ru.otus.dobrovolsky.message.Msg;
import ru.otus.dobrovolsky.message.channel.MsgChannel;
import ru.otus.dobrovolsky.message.channel.SocketClientChannel;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class MsgServer {
    private static final Logger LOGGER = Logger.getLogger(MsgServer.class.getName());

    private static final int THREADS_NUMBER = 4;
    private static final int PORT = 5050;
    private static final int DELAY = 100;

    private final ExecutorService executor;
    private final Map<MsgChannel, Address> registeredChannels;

    public MsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        this.registeredChannels = new HashMap<>();
    }

    public void start() throws Exception {
        executor.submit(this::getMessage);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket client = serverSocket.accept();
                SocketClientChannel channel = new SocketClientChannel(client);
                channel.init();
                channel.addShutdownRegistration(() -> registeredChannels.remove(channel));
                registeredChannels.put(channel, null);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void getMessage() {
        while (true) {
            for (Map.Entry<MsgChannel, Address> entry : registeredChannels.entrySet()) {
                MsgChannel channel = entry.getKey();
                Address addressFrom = entry.getValue();

                if (addressFrom != null) {
                    Msg message = channel.poll();
                    if (message != null) {
                        MsgChannel toChannel = getChannel(message.getTo());
                        LOGGER.info("Received the message from: " + message.getFrom() + " to:   " + message.getTo() + " message:   " + message);
                        if (registeredChannels.containsKey(toChannel)) {
                            if (message.getClass().getName().equals(MsgGetCacheAnswer.class.getName())) {
                                LOGGER.info("Sending cache answer message: " + message.getFrom() + " to:   " + message.getTo() + " message:   " + message);
                                LOGGER.info("MsgServer :    Current CACHE status: " + message.getValue());
                                getChannel(message.getTo()).send(message);
                            } else {
                                LOGGER.info("Sending message:    " + message.getClass().getName() + " from:   " + message.getFrom() + "   to:    " + message.getTo());
                                getChannel(message.getTo()).send(message);
                            }
                        } else {
                            LOGGER.info("Receiver for message:  " + message + " wasn't registered yet");
                        }
                        LOGGER.info("Address FROM:  " + getChannel(message.getFrom()));
                        LOGGER.info("Address TO:  " + getChannel(message.getTo()));
                    }
                } else {
                    Msg message = channel.poll();
                    if (message != null) {
                        LOGGER.info("Received the message from: " + message.getFrom() + " to:   " + message.getTo() + " message:   " + message);
                        if (message.getClass().getName().equals(MsgRegistration.class.getName())) {
                            Address from = message.getFrom();
                            registeredChannels.put(channel, from);
                            LOGGER.info("Registered address:    " + from.getId());
                            Msg msgRegistrationAnswer = new MsgRegistrationAnswer(message.getTo(), message.getFrom());
                            channel.send(msgRegistrationAnswer);
                        }
                    }
                }
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private MsgChannel getChannel(Address address) {
        for (Map.Entry<MsgChannel, Address> entry : registeredChannels.entrySet()) {
            if (entry.getValue().equals(address)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
